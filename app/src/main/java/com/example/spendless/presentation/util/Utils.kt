package com.example.spendless.presentation.util

object Utils {
    val usernameRegex = "^[a-zA-Z0-9]*+$".toRegex()

    //2000ms = 2s
    var BANNER_SHOW_TIME: Long = 2000
    val pinRegex = "^[0-9]*+$".toRegex()

    fun updateBannerTime(bannerTime: Long) {
        BANNER_SHOW_TIME = bannerTime
    }

}