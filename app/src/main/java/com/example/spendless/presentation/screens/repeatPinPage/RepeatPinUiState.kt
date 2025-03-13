package com.example.spendless.presentation.screens.repeatPinPage

import com.example.spendless.data.model.Username

data class RepeatPinUiState(
    val username: Username = Username(),
    val repeatPin: String = "",
    //ellipseList size is 5 fixed, and all false as default
    val ellipseList: List<Boolean> = List(5) { false },
    )
