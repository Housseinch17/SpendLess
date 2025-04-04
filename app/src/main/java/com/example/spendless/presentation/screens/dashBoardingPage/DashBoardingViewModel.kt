package com.example.spendless.presentation.screens.dashBoardingPage

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.spendless.domain.usecase.currentTime.TimeUseCase
import com.example.spendless.domain.usecase.local.LocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

sealed interface DashBoardingActions {
    data class UpdateUsername(val username: String) : DashBoardingActions
    data object PromptPin : DashBoardingActions
}

sealed interface DashBoardingEvents {
    data object PromptPin : DashBoardingEvents
}

@HiltViewModel
class DashBoardingViewModel @Inject constructor(
    private val localUseCase: LocalUseCase,
    private val timeUseCase: TimeUseCase,

    ) : ViewModel() {
    private val _dashBoardingUiState: MutableStateFlow<DashBoardingUiState> =
        MutableStateFlow(DashBoardingUiState())
    val dashBoardingUiState: StateFlow<DashBoardingUiState> = _dashBoardingUiState.asStateFlow()

    private val _dashBoardingEvents: Channel<DashBoardingEvents> = Channel()
    val dashBoardingEvents = _dashBoardingEvents.receiveAsFlow()


    fun onAction(dashBoardingActions: DashBoardingActions) {
        when (dashBoardingActions) {
            is DashBoardingActions.UpdateUsername -> updateUsername(username = dashBoardingActions.username)
            DashBoardingActions.PromptPin -> {

            }
        }
    }

    private suspend fun promptPin() {
        val username = _dashBoardingUiState.value.username

        //get user current time
        val userCurrentTime = localUseCase.getUserCurrentTime(enteredUsername = username)

        //get user sessionExpiryDuration
        val sessionExpiryDuration =
            localUseCase.getUserSessionExpiryDuration(enteredUsername = username)
        
    }

    private fun updateUsername(username: String) {
        _dashBoardingUiState.update { newState ->
            newState.copy(username = username)
        }
    }

}