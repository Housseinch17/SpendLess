package com.example.spendless.presentation.screens.createPinPage

import android.util.Log
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

sealed interface CreatePinEvents {
    data class RepeatPinPage(val username: Username) : CreatePinEvents
    data object NavigateBack : CreatePinEvents
}

sealed interface CreatePinActions {
    data class UpdateUsername(val username: String) : CreatePinActions
    data class UpdatePin(val pin: String) : CreatePinActions
    data object RemovePin : CreatePinActions
    data object NavigateBack : CreatePinActions
}

@HiltViewModel
class CreatePinViewModel @Inject constructor() : ViewModel() {
    private val _createPinUiState: MutableStateFlow<CreatePinUiState> = MutableStateFlow(
        CreatePinUiState()
    )
    val createPinUiState: StateFlow<CreatePinUiState> = _createPinUiState.asStateFlow()

    private val _events: Channel<CreatePinEvents> = Channel()
    val events = _events.receiveAsFlow()

    fun onActions(onActions: CreatePinActions) {
        when (onActions) {
            is CreatePinActions.UpdateUsername -> updateUsername(onActions.username)
            CreatePinActions.NavigateBack -> {
                viewModelScope.launch {
                    navigateBack()
                }
            }

            is CreatePinActions.UpdatePin -> {
                viewModelScope.launch {
                    updatePin(onActions.pin)
                }
            }

            CreatePinActions.RemovePin -> removePin()
        }
    }

    private fun removePin() {
        if (_createPinUiState.value.pin.isNotEmpty()) {
            _createPinUiState.update { newState ->
                newState.copy(pin = newState.pin.dropLast(1))
            }
            //update ellipseList
            val ellipseList = updateEllipseList(_createPinUiState.value.pin)

            _createPinUiState.update { newState ->
                newState.copy(ellipseList = ellipseList)
            }
        }
    }

    private fun updateUsername(username: String) {
        _createPinUiState.update { newState ->
            newState.copy(username = username)
        }
    }

    private suspend fun updatePin(pin: String) {
        _createPinUiState.update { newState ->
            newState.copy(pin = newState.pin + pin)
        }

        //update ellipseList
        val ellipseList = updateEllipseList(_createPinUiState.value.pin)

        _createPinUiState.update { newState ->
            newState.copy(ellipseList = ellipseList)
        }

//        Log.d("MyTag", "createPin updatePin: ${_createPinUiState.value.pin}")
//        Log.d("MyTag","createPin updatePin: ${_createPinUiState.value.ellipseList}")


        val newPin = _createPinUiState.value.pin
//        Log.d("MyTag","new Pin $newPin")
        if (newPin.length == 5) {
            val username = Username(username = _createPinUiState.value.username, pin = newPin)
//            Log.d("MyTag", "createPin updatePin: username: $username")
            _events.send(CreatePinEvents.RepeatPinPage(username = username))
        }
    }

    private suspend fun navigateBack() {
        _events.send(CreatePinEvents.NavigateBack)
    }

}
