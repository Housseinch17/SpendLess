package com.example.spendless.presentation.screens.promptPinPage

data class PromptPinUiState(
    val username: String = "hhh",
    val pin: String = "",
    val firstText: String = "Hello, Houssein chahine",
    val secondText: String = "Enter your pin",
    //ellipseList size is 5 fixed, and all false as default
    val ellipseList: List<Boolean> = List(5) { false },
    val buttonEnabled: Boolean = true,
    val counter: Int = 0,
    val timer: Int = 30000,
)
