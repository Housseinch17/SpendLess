package com.example.spendless.presentation.screens.promptPinPage

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PromptPinViewModel @Inject constructor() : ViewModel() {
    private val _promptPinUiState: MutableStateFlow<PromptPinUiState> = MutableStateFlow(PromptPinUiState())
    val promptPinUiState: StateFlow<PromptPinUiState> = _promptPinUiState.asStateFlow()


}