package com.example.spendless.presentation.screens.createPinPage

data class CreatePinUiState(
    val pin: String = "",
    val username: String = "",
    //ellipseList size is 5 fixed, and all false as default
    val ellipseList: List<Boolean> = List(5) { false },
    val buttonEnabled: Boolean = true,
)