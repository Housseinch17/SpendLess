package com.example.spendless.presentation.util

import android.util.Log
import com.example.spendless.data.model.PreferencesFormat
import com.example.spendless.presentation.Currency
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale
import android.util.Base64


object Utils {
    // Convert encrypted ByteArray to Base64 String
    fun ByteArray.toBase64(): String = Base64.encodeToString(this, Base64.DEFAULT)

    // Convert Base64 String back to ByteArray
    fun String.fromBase64(): ByteArray = Base64.decode(this, Base64.DEFAULT)

    val usernameRegex = "^[a-zA-Z0-9]*+$".toRegex()

    //2000ms = 2s
    var BANNER_SHOW_TIME: Long = 2000
    val pinRegex = "^[0-9]*+$".toRegex()

    fun updateBannerTime(bannerTime: Long) {
        BANNER_SHOW_TIME = bannerTime
    }

    val keyboardSet: List<String> =
        listOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "remove")

    val expensesList: List<Boolean> = listOf(true, false)

    val currencyList: List<Currency> = listOf(
        Currency(symbol = "$", name = "US Dollar (USD)"),
        Currency(symbol = "€", name = "Euro (EUR)"),
        Currency(symbol = "£", name = "British Pound Sterling (GBP)"),
        Currency(symbol = "¥", name = "Japanese Yen (JPY)"),
        Currency(symbol = "CHF", name = "Swiss Franc  (CHF)"),
        Currency(symbol = "C$", name = "Canadian Dollar (CAD)"),
        Currency(symbol = "A$", name = "Australian Dollar (AUD)"),
        Currency(symbol = " ¥", name = "Chinese Yuan Renminbi (CNY)"),
        Currency(symbol = " ₹", name = "Indian Rupee (INR)"),
        Currency(symbol = " R", name = "South African Rand (ZAR)"),
    )

    val decimalSeparatorList: List<String> = listOf(
        "1.00",
        "1,00"
    )

    val thousandsSeparator: List<String> = listOf(
        "1.000",
        "1,000",
        "1 000"
    )

    fun updateEllipseList(pin: String): List<Boolean> {
        //list size 5 fixed
        //pin digits will show true if exists and false if empty
        // pin = 32 -> 2 digits -> true,true,false,false,false,
        return List(5) { index -> index < pin.length }
    }

    fun hasSameSeparators(decimalSeparator: String, thousandsSeparator: String): Boolean{
            val decimalSeparator: Char = when (decimalSeparator) {
                "1.00" -> '.'
                "1,00" -> ','
                else -> 'd'
            }


        Log.d("Separator","dec $decimalSeparator")

            val thousandsSeparator = when (thousandsSeparator) {
                "1.000" -> '.'
                "1,000" -> ','
                "1 000" -> ' '
                else -> 't'
            }

        Log.d("Separator","th $thousandsSeparator")
            return decimalSeparator == thousandsSeparator
    }

    fun formatPrice(amount: Double, preferences: PreferencesFormat): String {
        val absAmount = kotlin.math.abs(amount)

        // Determine the decimal separator
        val decimalSeparators = when (preferences.decimalSeparator) {
            "1.00" -> '.'
            "1,00" -> ','
            else -> '.'
        }

        // Determine the thousands separator
        val thousandsSeparator = when (preferences.thousandsSeparator) {
            "1.000" -> '.'
            "1,000" -> ','
            "1 000" -> ' '
            else -> '.'
        }

        // Configure DecimalFormatSymbols
        val symbols = DecimalFormatSymbols(Locale.getDefault()).apply {
            groupingSeparator = thousandsSeparator
            decimalSeparator = decimalSeparators
        }

        // Apply formatting pattern
        val decimalFormat = DecimalFormat("#,##0.00", symbols)

        var formattedNumber = decimalFormat.format(absAmount)

        // Prepend currency symbol
        formattedNumber = "${preferences.currency.symbol}$formattedNumber"

        // Apply expense format
        formattedNumber = if (preferences.expensesIsNegative) "-$formattedNumber" else "($formattedNumber)"

        return formattedNumber
    }
}