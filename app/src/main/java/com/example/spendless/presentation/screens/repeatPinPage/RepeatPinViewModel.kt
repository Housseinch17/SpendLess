package com.example.spendless.presentation.screens.repeatPinPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendless.data.model.Username
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

sealed interface RepeatPinEvents {
    data object NavigateBack : RepeatPinEvents
    data class OnboardingPreferencesPage(val username: Username) : RepeatPinEvents
    data object ShowBanner : RepeatPinEvents
    data class Error(val error: String) : RepeatPinEvents
}

sealed interface RepeatPinActions {
    data class UpdateUsername(val username: Username) : RepeatPinActions
    data class UpdatePin(val pin: String) : RepeatPinActions
    data object RemovePin : RepeatPinActions
    data object NavigateBack : RepeatPinActions
}

@HiltViewModel
class RepeatPinViewModel @Inject constructor() : ViewModel() {
    private val _repeatPinUiState: MutableStateFlow<RepeatPinUiState> = MutableStateFlow(
        RepeatPinUiState()
    )
    val repeatPinUiState: StateFlow<RepeatPinUiState> = _repeatPinUiState.asStateFlow()

    private val _events: Channel<RepeatPinEvents> = Channel()
    val events = _events.receiveAsFlow()

    fun onActions(onActions: RepeatPinActions) {
        when (onActions) {
            RepeatPinActions.NavigateBack -> viewModelScope.launch {
                navigateBack()
            }

            RepeatPinActions.RemovePin -> removePin()
            is RepeatPinActions.UpdatePin -> {
                viewModelScope.launch {
                    updatePin(pin = onActions.pin)
                }
            }

            is RepeatPinActions.UpdateUsername -> updateUsername(username = onActions.username)
        }
    }

    private fun removePin() {
        if(_repeatPinUiState.value.repeatPin.length == 5){
            _repeatPinUiState.update { newState ->
                newState.copy(buttonEnabled = true)
            }
        }
        if (_repeatPinUiState.value.repeatPin.isNotEmpty()) {
            _repeatPinUiState.update { newState ->
                newState.copy(repeatPin = newState.repeatPin.dropLast(1))
            }
            //update ellipseList
            val ellipseList = updateEllipseList(_repeatPinUiState.value.repeatPin)

            _repeatPinUiState.update { newState ->
                newState.copy(ellipseList = ellipseList)
            }
        }
    }

    private fun updateUsername(username: Username) {
        _repeatPinUiState.update { newState ->
            newState.copy(username = username)
        }
    }

    private suspend fun updatePin(pin: String) {
        _repeatPinUiState.update { newState ->
            newState.copy(repeatPin = newState.repeatPin + pin)
        }

        //update ellipseList
        val ellipseList = updateEllipseList(_repeatPinUiState.value.repeatPin)

        _repeatPinUiState.update { newState ->
            newState.copy(ellipseList = ellipseList)
        }


        val newRepeatPin = _repeatPinUiState.value.repeatPin

//        Log.d("MyTag", "repeat Pin: ${_repeatPinUiState.value.repeatPin}")
//        Log.d("MyTag", "create Pin: ${_repeatPinUiState.value.username.pin}")

        //if repeatPin == createPin navigate to OnBoardingPreferences
        if (newRepeatPin.length == 5) {
            if (newRepeatPin != _repeatPinUiState.value.username.pin) {
                _events.send(RepeatPinEvents.ShowBanner)
                resetUiState()
            } else {
                _repeatPinUiState.update { newState->
                    newState.copy(buttonEnabled = false)
                }
                _events.send(
                    RepeatPinEvents.OnboardingPreferencesPage(
                        username = _repeatPinUiState.value.username
                    )
                )
            }
        }
    }

    private fun resetUiState() {
        _repeatPinUiState.value =
            RepeatPinUiState().copy(username = _repeatPinUiState.value.username)
//        Log.d("MyTag","${_repeatPinUiState.value}")
    }

    private suspend fun navigateBack() {
        _events.send(RepeatPinEvents.NavigateBack)
    }
}