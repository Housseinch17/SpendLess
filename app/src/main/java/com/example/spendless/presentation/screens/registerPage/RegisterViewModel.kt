package com.example.spendless.presentation.screens.registerPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendless.domain.usecase.local.LocalUseCase
import com.example.spendless.presentation.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface RegisterEvents {
    data class NavigateToCreatePinPage(val username: String) : RegisterEvents
    data object NavigateToLogInPage : RegisterEvents
    data object ShowBanner : RegisterEvents
    data object ShowError : RegisterEvents
}

sealed interface RegisterActions {
    data class UpdateUsername(val usernameValue: String) : RegisterActions
    data object NextButton : RegisterActions
    data object AlreadyHaveAnAccount : RegisterActions

}

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val localUseCase: LocalUseCase,
) : ViewModel() {
    private val _registerUiState: MutableStateFlow<RegisterUiState> =
        MutableStateFlow(RegisterUiState())
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    private val _events: Channel<RegisterEvents> = Channel()
    val events = _events.receiveAsFlow()

    fun onActions(onActions: RegisterActions) {
        when (onActions) {
            is RegisterActions.UpdateUsername -> {
                updateUsername(onActions.usernameValue)
            }

            is RegisterActions.NextButton -> {
                viewModelScope.launch {
                    next()
                }
            }

            RegisterActions.AlreadyHaveAnAccount -> {
                viewModelScope.launch {
                    alreadyHaveAccount()
                }
            }
        }
    }

    private fun updateUsername(usernameValue: String) {
        viewModelScope.launch {
            if (usernameValue.matches(Utils.usernameRegex)) {
                _registerUiState.update { newState ->
                    newState.copy(
                        usernameValue = usernameValue,
                        isNextEnabled = true
                    )
                }
            }
        }
    }


    private suspend fun next() {
        val usernameLength = _registerUiState.value.usernameValue.length
        if (usernameLength in 3..14) {
            //check if username is taken
            val isUsernameExists = isUsernameExists()
            Log.d("MyTag","isUsernameExists: $isUsernameExists")
            if (isUsernameExists) {
                _events.send(RegisterEvents.ShowBanner)
                _registerUiState.update { newState ->
                    newState.copy(isNextEnabled = false)
                }
            } else {
                _events.send(RegisterEvents.NavigateToCreatePinPage(username = _registerUiState.value.usernameValue))
            }
        }else{
            _events.send(RegisterEvents.ShowError)
        }
    }

    private suspend fun isUsernameExists(): Boolean {
        val isUsernameExists = localUseCase.isUsernameExists(_registerUiState.value.usernameValue)
        return isUsernameExists
    }

    private suspend fun alreadyHaveAccount() {
        _events.send(RegisterEvents.NavigateToLogInPage)
    }


}