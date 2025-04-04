package com.example.spendless.presentation.screens.promptPinPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendless.domain.usecase.local.LocalUseCase
import com.example.spendless.domain.usecase.security.SecurityUseCase
import com.example.spendless.presentation.util.Utils.fromBase64
import com.example.spendless.presentation.util.Utils.updateEllipseList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface PromptPinEvents {
    data object PinCorrect : PromptPinEvents
    data object PinIncorrect : PromptPinEvents
}

sealed interface PromptPinActions {
    data class UpdatePin(val pin: String) : PromptPinActions
    data object RemovePin : PromptPinActions
}

@HiltViewModel
class PromptPinViewModel @Inject constructor(
    private val localUseCase: LocalUseCase,
    private val securityUseCase: SecurityUseCase,
) : ViewModel() {
    private val _promptPinUiState: MutableStateFlow<PromptPinUiState> =
        MutableStateFlow(PromptPinUiState())
    val promptPinUiState: StateFlow<PromptPinUiState> = _promptPinUiState.asStateFlow()

    private val _events: Channel<PromptPinEvents> = Channel()
    val events = _events.receiveAsFlow()

    fun onActions(promptPinActions: PromptPinActions) {
        when (promptPinActions) {
            PromptPinActions.RemovePin -> {

            }

            is PromptPinActions.UpdatePin -> {
                viewModelScope.launch {
                    updatePin(pin = promptPinActions.pin)
                }
            }

        }
    }


    private suspend fun updatePin(pin: String) {
        _promptPinUiState.update { newState ->
            newState.copy(pin = newState.pin + pin)
        }

        //update ellipseList
        val ellipseList = updateEllipseList(_promptPinUiState.value.pin)

        _promptPinUiState.update { newState ->
            newState.copy(ellipseList = ellipseList)
        }

        //read updated pin from state
        val newPin = _promptPinUiState.value.pin

        val username = _promptPinUiState.value.username

        if (newPin.length == 5) {
            val pinIsCorrect = pinIsCorrect(username = username)

            if (pinIsCorrect) {
                _events.send(PromptPinEvents.PinCorrect)
            } else {
                _promptPinUiState.update { newState ->
                    newState.copy(counter = newState.counter + 1)
                }
                if (_promptPinUiState.value.counter == 3) {
                    _events.send(PromptPinEvents.PinIncorrect)

                    val lockedOutDuration =
                        localUseCase.getUserLockedOutDuration(enteredUsername = username)

                    _promptPinUiState.update { newState ->
                        newState.copy(
                            firstText = "Too many failed attempts",
                            secondText = "Try your PIN again in 00:${_promptPinUiState.value.timer}",
                            buttonEnabled = false
                        )
                    }

                    for(time in lockedOutDuration.lockedOutDuration downTo 0){
                        _promptPinUiState.update { newState->
                            newState.copy(secondText = "Try your PIN again in 00:$time")
                        }
                    }

                    _promptPinUiState.update { newState ->
                        newState.copy(counter = 0, buttonEnabled = true)
                    }
                }
            }
        }
    }

    private suspend fun pinIsCorrect(username: String): Boolean {
        val encryptedPinAsString = localUseCase.getUserStoredPin(enteredUsername = username)
        Log.d("MyTag", "encryptedPinBase64: $encryptedPinAsString")
        val encryptedPinToByteArray = encryptedPinAsString?.fromBase64()
        Log.d("MyTag", "encryptedPinToByteArray: $encryptedPinToByteArray")
        val decryptedPinASByteArray =
            encryptedPinToByteArray?.let { securityUseCase.decrypt(bytes = it) }
        Log.d("MyTag", "decryptedPinASByteArray :$decryptedPinASByteArray")
        val decryptedPinAsString = decryptedPinASByteArray?.toString(Charsets.UTF_8) ?: ""
        Log.d("MyTag", "decryptedPinAsString: $decryptedPinAsString")

        val pin = _promptPinUiState.value.pin

        //we fetch encrypted pin of the username, later we decrypt it to get it as string
        //if it's the same as the one entered means the user is valid
        val isValidUser = decryptedPinAsString == pin
        Log.d("MyTag", "isValidUser: $isValidUser")

        return isValidUser
    }
}