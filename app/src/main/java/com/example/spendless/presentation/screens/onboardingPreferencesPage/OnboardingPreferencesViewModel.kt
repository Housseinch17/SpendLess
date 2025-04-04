package com.example.spendless.presentation.screens.onboardingPreferencesPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendless.data.model.Username
import com.example.spendless.domain.usecase.local.LocalUseCase
import com.example.spendless.domain.usecase.security.SecurityUseCase
import com.example.spendless.data.model.Currency
import com.example.spendless.domain.usecase.currentTime.TimeUseCase
import com.example.spendless.presentation.util.Utils.formatPrice
import com.example.spendless.presentation.util.Utils.hasSameSeparators
import com.example.spendless.presentation.util.Utils.toBase64
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface OnboardingPreferencesEvents {
    data object NavigateBack : OnboardingPreferencesEvents
    data class ShowError(val error: String) : OnboardingPreferencesEvents
    data class NavigateToDashBoard(val username: String) : OnboardingPreferencesEvents
}

sealed interface OnboardingPreferencesActions {
    data object NavigateBack : OnboardingPreferencesActions
    data class UpdateUsername(val username: Username) : OnboardingPreferencesActions
    data class UpdateExpenses(val expensesIsNegative: Boolean) : OnboardingPreferencesActions
    data class UpdateCurrency(val currency: Currency) : OnboardingPreferencesActions
    data class UpdateDecimalSeparator(val decimalSeparator: String) : OnboardingPreferencesActions
    data class UpdateThousandsSeparator(val thousandsSeparator: String) :
        OnboardingPreferencesActions

    data object OnExpand : OnboardingPreferencesActions
    data object StartTracking : OnboardingPreferencesActions
    data object UpdatePrice : OnboardingPreferencesActions

}

@HiltViewModel
class OnboardingPreferencesViewModel @Inject constructor(
    private val localUseCase: LocalUseCase,
    private val securityUseCase: SecurityUseCase,
    private val timeUseCase: TimeUseCase,
) : ViewModel() {
    private val _onboardingPreferencesUiState: MutableStateFlow<OnboardingPreferencesUiState> =
        MutableStateFlow(
            OnboardingPreferencesUiState()
        )
    val onboardingPreferencesUiState = _onboardingPreferencesUiState.asStateFlow()

    private val _onboardingPreferencesEvents: Channel<OnboardingPreferencesEvents> = Channel()
    val onboardingPreferencesEvents = _onboardingPreferencesEvents.receiveAsFlow()


    fun onActions(onActions: OnboardingPreferencesActions) {
        when (onActions) {
            OnboardingPreferencesActions.NavigateBack -> {
                viewModelScope.launch {
                    navigateBack()
                }
            }

            is OnboardingPreferencesActions.UpdateUsername -> updateUsername(username = onActions.username)
            is OnboardingPreferencesActions.UpdateExpenses -> updateExpenses(expensesIsNegative = onActions.expensesIsNegative)
            is OnboardingPreferencesActions.UpdateCurrency -> updateCurrency(currency = onActions.currency)
            is OnboardingPreferencesActions.UpdateDecimalSeparator -> updateDecimalSeparator(
                decimalSeparator = onActions.decimalSeparator
            )

            is OnboardingPreferencesActions.UpdateThousandsSeparator -> updateThousandsSeparator(
                thousandsSeparator = onActions.thousandsSeparator
            )

            OnboardingPreferencesActions.OnExpand -> onExpand()
            OnboardingPreferencesActions.StartTracking -> {
                viewModelScope.launch {
                    startTracking()
                }
            }

            OnboardingPreferencesActions.UpdatePrice -> updatePrice()
        }
    }

    private fun updatePrice() {
        val formattedPrice: String = formatPrice(
            amount = _onboardingPreferencesUiState.value.price,
            preferences = _onboardingPreferencesUiState.value.preferencesFormat
        )
        _onboardingPreferencesUiState.update { newState ->
            newState.copy(formattedPrice = formattedPrice)
        }
    }


    private suspend fun startTracking() {
        val preferencesFormat = _onboardingPreferencesUiState.value.preferencesFormat
//        Log.d("MyTag","preferencesFormat: $preferencesFormat")
        val user = _onboardingPreferencesUiState.value.user
//        Log.d("MyTag", user.username)
        try {
            Log.d("MyTag", "userPin: ${user.pin}")
            //convert pin to ByteArray()
            val pinToByteArray = user.pin.toByteArray(Charsets.UTF_8)
            //encrypt converted pin
            val encryptedPin = securityUseCase.encrypt(bytes = pinToByteArray)
            //convert encrypted pin to string from ByteArray
            val encryptedBytePinToString = encryptedPin.toBase64()
            Log.d("MyTag", "encryptedPinToBase: $encryptedBytePinToString")

            //get currentTime
            val currentTime = timeUseCase.getCurrentTime()

            //save user into room database it takes SessionExpiryDuration and LockedOutDuration by default
            val userToSave = user.copy(
                currentTime = currentTime,
                preferencesFormat = preferencesFormat,
                pin = encryptedBytePinToString
            )

//            Log.d("MyTag", "userToSave: $userToSave")
            localUseCase.saveUsername(username = userToSave)
            //send events to navigate to dashBoard
            _onboardingPreferencesEvents.send(
                OnboardingPreferencesEvents.NavigateToDashBoard(
                    username = user.username
                )
            )
            Log.d("MyTag", "User saved: $userToSave")
        } catch (e: Exception) {
            _onboardingPreferencesEvents.send(OnboardingPreferencesEvents.ShowError(error = e.message.toString()))
        }
    }

    private fun onExpand() {
        _onboardingPreferencesUiState.update { newState ->
            newState.copy(isExpanded = !newState.isExpanded)
        }
    }

    private fun updateUsername(username: Username) {
        _onboardingPreferencesUiState.update { newState ->
            newState.copy(user = username)
        }
    }

    private fun updateExpenses(expensesIsNegative: Boolean) {
        _onboardingPreferencesUiState.update { newState ->
            newState.copy(preferencesFormat = newState.preferencesFormat.copy(expensesIsNegative = expensesIsNegative))
        }
        updatePrice()
    }

    private fun updateCurrency(currency: Currency) {
        _onboardingPreferencesUiState.update { newState ->
            newState.copy(preferencesFormat = newState.preferencesFormat.copy(currency = currency))
        }
        updatePrice()
    }

    private fun updateDecimalSeparator(decimalSeparator: String) {
        val hasSameSeparators = hasSameSeparators(
            decimalSeparator = decimalSeparator,
            thousandsSeparator = _onboardingPreferencesUiState.value.preferencesFormat.thousandsSeparator
        )

        Log.d(
            "MyTag",
            "dec: $decimalSeparator th: ${_onboardingPreferencesUiState.value.preferencesFormat.thousandsSeparator}"
        )
        _onboardingPreferencesUiState.update { newState ->
            newState.copy(
                preferencesFormat = newState.preferencesFormat.copy(decimalSeparator = decimalSeparator),
                hasSameOperator = hasSameSeparators
            )
        }
        Log.d("MyTag", "${_onboardingPreferencesUiState.value.hasSameOperator}")

        //update price after changing format
        updatePrice()
    }

    private fun updateThousandsSeparator(thousandsSeparator: String) {
        val hasSameSeparators = hasSameSeparators(
            decimalSeparator = _onboardingPreferencesUiState.value.preferencesFormat.decimalSeparator,
            thousandsSeparator = thousandsSeparator
        )
        Log.d(
            "MyTag",
            "th: $thousandsSeparator dec: ${_onboardingPreferencesUiState.value.preferencesFormat.decimalSeparator}"
        )
        _onboardingPreferencesUiState.update { newState ->
            newState.copy(
                preferencesFormat = newState.preferencesFormat.copy(thousandsSeparator = thousandsSeparator),
                hasSameOperator = hasSameSeparators
            )
        }

        Log.d("MyTag", "${_onboardingPreferencesUiState.value.hasSameOperator}")

        //update price after changing format
        updatePrice()
    }

    private suspend fun navigateBack() {
        _onboardingPreferencesEvents.send(
            OnboardingPreferencesEvents
                .NavigateBack
        )
    }

}