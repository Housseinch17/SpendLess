package com.example.spendless.presentation.screens.registerPage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spendless.domain.usecase.LocalUseCase
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
    data class NavigateToPinPage(val username: String) : RegisterEvents
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
    private val useCase: LocalUseCase,
) : ViewModel() {
    private val _registerUiState: MutableStateFlow<RegisterUiState> =
        MutableStateFlow(RegisterUiState())
    val registerUiState: StateFlow<RegisterUiState> = _registerUiState.asStateFlow()

    private val _events: Channel<RegisterEvents> = Channel()
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            getAllUsername()
        }
    }

    fun onActions(registerActions: RegisterActions) {
        when (registerActions) {
            is RegisterActions.UpdateUsername -> {
                updateUsername(registerActions.usernameValue)
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
            //update isUsernameTaken value
            usernameAlreadyExists()
            if (_registerUiState.value.isUsernameTaken) {
                _events.send(RegisterEvents.ShowBanner)
                _registerUiState.update { newState ->
                    newState.copy(isNextEnabled = false)
                }
            } else {
                _events.send(RegisterEvents.NavigateToPinPage(username = _registerUiState.value.usernameValue))
            }
        }else{
            Log.d("MyTag","Here")
            _events.send(RegisterEvents.ShowError)
            return
        }
    }

    private fun usernameAlreadyExists() {
        val registerUiStateValue = _registerUiState.value
        val username = registerUiStateValue.usernameValue
        val isUsernameTaken = registerUiStateValue.usernameCreated.map {
            it.username
        }.contains(username)
        Log.d("MyTag", "$isUsernameTaken")
        _registerUiState.update { newState ->
            newState.copy(isUsernameTaken = isUsernameTaken)
        }
    }


    private suspend fun getAllUsername() {
        useCase.getAllUsername().collect { newUser ->
            _registerUiState.update { newState ->
                newState.copy(usernameCreated = newState.usernameCreated + newUser)
            }
        }
    }

    private suspend fun alreadyHaveAccount() {
        _events.send(RegisterEvents.NavigateToLogInPage)
    }


}