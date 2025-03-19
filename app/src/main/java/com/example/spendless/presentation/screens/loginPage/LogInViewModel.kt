package com.example.spendless.presentation.screens.loginPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendless.domain.usecase.local.LocalUseCase
import com.example.spendless.presentation.util.Utils.pinRegex
import com.example.spendless.presentation.util.Utils.usernameRegex
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface LogInEvents {
    data object RegisterAccount : LogInEvents
    data class ShowError(val error: String) : LogInEvents
    data class LoggedIn(val username: String): LogInEvents
}

sealed interface LogInActions {
    data class UpdateUsername(val username: String) : LogInActions
    data class UpdatePin(val pin: String) : LogInActions
    data class LogIn(val username: String, val pin: String) : LogInActions
    data object RegisterAccount : LogInActions
}

@HiltViewModel
class LogInViewModel @Inject constructor(
    private val localUseCase: LocalUseCase
) : ViewModel() {
    private val _logInUiState: MutableStateFlow<LogInUiState> = MutableStateFlow(LogInUiState())
    val logInUiState: StateFlow<LogInUiState> = _logInUiState.asStateFlow()

    private val _events: Channel<LogInEvents> = Channel()
    val events = _events.receiveAsFlow()

    fun onActions(onActions: LogInActions) {
        when (onActions) {
            is LogInActions.UpdatePin -> {
                viewModelScope.launch {
                    updatePin(pin = onActions.pin)
                }
            }
            is LogInActions.UpdateUsername -> {
                viewModelScope.launch {
                    updateUsername(username = onActions.username)
                }
            }
            is LogInActions.LogIn -> {
                viewModelScope.launch {
                    logIn(
                        username = onActions.username,
                        pin = onActions.pin
                    )
                }
            }

            LogInActions.RegisterAccount -> {
                viewModelScope.launch {
                    registerAccount()
                }
            }
        }
    }

    private suspend fun registerAccount() {
        _events.send(LogInEvents.RegisterAccount)
    }

    private suspend fun logIn(username: String, pin: String) {
        if(username.isNotEmpty() && pin.isNotEmpty()){
            val isValidUser = localUseCase.isValidUser(enteredUsername = username, enteredPin = pin)

            if (isValidUser) {
                _events.send(LogInEvents.LoggedIn(username = username))
            } else {
                _events.send(LogInEvents.ShowError(error = "User is not valid!"))
            }
        }else{
            _events.send(LogInEvents.ShowError(error = "Username and pin should not be empty!"))
        }

    }


    private suspend fun updateUsername(username: String) {
        if (_logInUiState.value.username.length <= 14) {
            if (username.matches(usernameRegex)) {
                _logInUiState.update { newState ->
                    newState.copy(username = username)
                }
            }
        }else{
            _events.send(LogInEvents.ShowError("Username reached max"))
        }
    }

    private suspend fun updatePin(pin: String) {
        if (_logInUiState.value.pin.length <= 5) {
            if (pin.matches(pinRegex))
                _logInUiState.update { newState ->
                    newState.copy(pin = pin)
                }
        }else{
            _events.send(LogInEvents.ShowError("Pin reached max"))
        }
    }

}