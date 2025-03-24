package com.example.spendless.presentation.screens.shared

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.spendless.domain.usecase.currentTime.CurrentTimeUseCase
import com.example.spendless.domain.usecase.local.LocalUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


sealed interface SharedActions {
    data class UpdateBannerText(val bannerText: String) : SharedActions
    data class ShowBanner(val bannerText: String) : SharedActions
    data object HideBanner : SharedActions
    data class UpdateUsername(val username: String) : SharedActions
    data object UpdateCurrentTime: SharedActions
}

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val localUseCase: LocalUseCase,
    private val currentTimeUseCase: CurrentTimeUseCase,
) : ViewModel() {
    private val _sharedUiState: MutableStateFlow<SharedUiState> = MutableStateFlow(SharedUiState())
    val sharedUiState: StateFlow<SharedUiState> = _sharedUiState.asStateFlow()


    fun onActions(sharedActions: SharedActions) {
        when (sharedActions) {
            is SharedActions.UpdateBannerText -> updateBannerText(sharedActions.bannerText)
            is SharedActions.ShowBanner -> {
                updateBannerText(sharedActions.bannerText)
                showBanner()
            }

            SharedActions.HideBanner -> hideBanner()
            is SharedActions.UpdateUsername -> updateUsername(username = sharedActions.username)
            is SharedActions.UpdateCurrentTime -> updateCurrentTime()
        }
    }

    private fun updateCurrentTime(){
        val currentTime = currentTimeUseCase.getCurrentTime()
        _sharedUiState.update { newState->
            newState.copy(currentTime = currentTime)
        }
        Log.d("MyTag","currentTime: $currentTime")
    }

    private fun updateUsername(username: String) {
        _sharedUiState.update { newState ->
            newState.copy(username = username)
        }
    }

    private fun showBanner() {
        _sharedUiState.update { newState ->
            newState.copy(showBanner = true)
        }
    }

    private fun hideBanner() {
        _sharedUiState.update { newState ->
            newState.copy(showBanner = false)
        }
    }

    private fun updateBannerText(bannerText: String) {
        _sharedUiState.update { newState ->
            newState.copy(bannerText = bannerText)
        }
    }

}