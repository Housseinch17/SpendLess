package com.example.spendless.presentation.screens.registerPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spendless.R
import com.example.spendless.presentation.theme.Light_OnBackground
import com.example.spendless.presentation.theme.Schemes_OnPrimary
import com.example.spendless.presentation.theme.Schemes_OnSurface
import com.example.spendless.presentation.theme.Schemes_OnSurface_Variant
import com.example.spendless.presentation.theme.Schemes_Primary
import com.example.spendless.presentation.theme.Typography
import com.example.spendless.presentation.util.SharedComponent

@Composable
fun RegisterPage(
    modifier: Modifier,
    registerUiState: RegisterUiState,
    registerActions: (RegisterActions) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SharedComponent(
            firstText = stringResource(R.string.welcome_spendLess),
            secondText = stringResource(R.string.create_unique_username),
        )

        UsernameTextField(
            modifier = Modifier.padding(horizontal = 26.dp),
            textValue = registerUiState.usernameValue,
            onValueUpdate = { usernameValue ->
                registerActions(RegisterActions.UpdateUsername(usernameValue = usernameValue))
            },
            onDone = {
                registerActions(RegisterActions.NextButton)
            })

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 26.dp),
            shape = RoundedCornerShape(16.dp),
            onClick = {
                registerActions(RegisterActions.NextButton)
            },
            colors = ButtonDefaults.buttonColors().copy(
                disabledContainerColor = Light_OnBackground,
                disabledContentColor = Schemes_OnSurface_Variant,
                containerColor = Schemes_Primary,
                contentColor = Schemes_OnPrimary,
            ),
            enabled = registerUiState.usernameValue.isNotEmpty() && registerUiState.isNextEnabled
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.next),
                    style = Typography.titleMedium,
                    color = LocalContentColor.current
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    modifier = Modifier.size(18.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    tint = LocalContentColor.current,
                    contentDescription = null
                )
            }
        }

        Spacer(modifier = Modifier.height(28.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                registerActions(RegisterActions.AlreadyHaveAnAccount)
            }
        ) {
            Text(
                modifier = Modifier, text = stringResource(R.string.already_have_account),
                style = Typography.titleMedium
            )
        }
    }
}

@Composable
fun UsernameTextField(
    modifier: Modifier,
    textValue: String,
    onValueUpdate: (String) -> Unit,
    onDone: () -> Unit,
) {
    // Create a FocusRequester
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(Unit) {
        //first launch make textField focused (open keyboard)
        focusRequester.requestFocus()
    }

    TextField(
        modifier = modifier
            .fillMaxWidth()
            .focusRequester(focusRequester),
        maxLines = 2,
        shape = RoundedCornerShape(16.dp),
        value = textValue,
        placeholder = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.username),
                style = Typography.displayMedium,
                color = LocalContentColor.current,
                textAlign = TextAlign.Center
            )
        },
        onValueChange = onValueUpdate,
        textStyle = Typography.displayMedium.copy(textAlign = TextAlign.Center),
        colors = TextFieldDefaults.colors().copy(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Light_OnBackground,
            focusedContainerColor = Light_OnBackground,
            unfocusedPlaceholderColor = Schemes_OnSurface_Variant,
            focusedTextColor = Schemes_OnSurface,
        ),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onDone()
            }),
    )
}