@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.spendless.presentation.screens.loginPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spendless.R
import com.example.spendless.presentation.theme.Schemes_OnPrimary
import com.example.spendless.presentation.theme.Schemes_OnSurface
import com.example.spendless.presentation.theme.Schemes_Primary
import com.example.spendless.presentation.theme.Typography
import com.example.spendless.presentation.util.SharedComponent

@Composable
fun LogInPage(
    modifier: Modifier,
    logInUiState: LogInUiState,
    logInActions: (LogInActions) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SharedComponent(
            firstText = stringResource(R.string.welcome_back),
            secondText = stringResource(R.string.enter_your_logIn_details),
        )

        UsernameTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp).clip(RoundedCornerShape(16.dp)).background(color = Schemes_OnPrimary),
            usernameValue = logInUiState.username,
            updateUserName = { newUsername ->
                logInActions(LogInActions.UpdateUsername(newUsername))
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        PinTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp).clip(RoundedCornerShape(16.dp)).background(color = Schemes_OnPrimary),
            pinValue = logInUiState.pin,
            updatePin = { newPin ->
                logInActions(
                    LogInActions.UpdatePin(
                        pin = newPin
                    )
                )
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        LogInButton(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            logInButton = {
                logInActions(
                    LogInActions.LogIn(
                        username = logInUiState.username,
                        pin = logInUiState.pin
                    )
                )
            })

        Spacer(modifier = Modifier.height(28.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                logInActions(LogInActions.RegisterAccount)
            }
        ) {
            Text(
                modifier = Modifier, text = stringResource(R.string.new_to_spendLess),
                style = Typography.titleMedium
            )
        }

    }
}

@Composable
fun UsernameTextField(
    modifier: Modifier,
    usernameValue: String,
    updateUserName: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = usernameValue,
        onValueChange = { newUserName ->
            updateUserName(newUserName)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.username),
                style = Typography.bodyMedium
            )
        },
        shape = RoundedCornerShape(16.dp),
        textStyle = Typography.bodyMedium.copy(LocalContentColor.current),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Schemes_OnSurface,
            unfocusedTextColor = Schemes_OnSurface,
            focusedBorderColor = Schemes_Primary,
            unfocusedBorderColor = Color.Transparent,
        ),
    )
}

@Composable
fun PinTextField(
    modifier: Modifier,
    pinValue: String,
    updatePin: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = pinValue,
        onValueChange = { newPin ->
            updatePin(newPin)
        },
        placeholder = {
            Text(
                text = stringResource(R.string.pin).uppercase(),
                style = Typography.bodyMedium
            )
        },
        textStyle = Typography.bodyMedium.copy(LocalContentColor.current),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedTextColor = Schemes_OnSurface,
            unfocusedTextColor = Schemes_OnSurface,
            focusedBorderColor = Schemes_Primary,
            unfocusedBorderColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(16.dp),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        )
    )
}

@Composable
fun LogInButton(modifier: Modifier, logInButton: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = {
            logInButton()
        },
        colors = ButtonDefaults.buttonColors(containerColor = Schemes_Primary)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.log_in),
            style = Typography.titleMedium.copy(color = Schemes_OnPrimary),
            textAlign = TextAlign.Center
        )
    }

}