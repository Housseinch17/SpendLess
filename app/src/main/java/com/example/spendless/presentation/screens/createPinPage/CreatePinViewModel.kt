package com.example.spendless.presentation.screens.createPinPage

import androidx.lifecycle.ViewModel
import com.example.spendless.data.model.Username
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed interface CreatePinEvents {
    data class RepeatPinPage (val username: Username): CreatePinEvents
}

sealed interface CreatePinActions {
    data class UpdateUsername(val username: String) : CreatePinActions
}

@HiltViewModel
class CreatePinViewModel @Inject constructor() : ViewModel() {
    private val _createPinUiState: MutableStateFlow<CreatePinUiState> = MutableStateFlow(
        CreatePinUiState()
    )
    val createPinUiState: StateFlow<CreatePinUiState> = _createPinUiState.asStateFlow()

    private val _events: Channel<CreatePinEvents> = Channel()
    val events = _events.receiveAsFlow()


    fun onActions(createPinActions: CreatePinActions) {
        when (createPinActions) {
            is CreatePinActions.UpdateUsername -> updateUsername(createPinActions.username)
        }
    }

    private fun updateUsername(username: String) {
        _createPinUiState.update { newState ->
            newState.copy(username = username)
        }
    }

}