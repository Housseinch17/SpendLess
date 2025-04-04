package com.example.spendless.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.spendless.R
import com.example.spendless.presentation.screens.createPinPage.CreatePinActions
import com.example.spendless.presentation.screens.promptPinPage.PromptPinActions
import com.example.spendless.presentation.screens.repeatPinPage.RepeatPinActions
import com.example.spendless.presentation.screens.shared.SharedActions
import com.example.spendless.presentation.theme.Schemes_Error
import com.example.spendless.presentation.theme.Schemes_Keyboard_OnPrimary
import com.example.spendless.presentation.theme.Schemes_Keyboard_Primary
import com.example.spendless.presentation.theme.Schemes_OnSurface
import com.example.spendless.presentation.theme.Typography


@Composable
fun SharedComponent(
    backButton: Boolean = false,
    logOutIcon: Boolean = false,
    firstText: String,
    secondText: String,
    navigateBack: () -> Unit = {},
    logOut: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(top = 8.dp, start = 4.dp, end = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (backButton) {
                IconButton(
                    modifier = Modifier.size(40.dp),
                    onClick = navigateBack,
                ) {
                    Icon(
                        modifier = Modifier,
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Schemes_OnSurface
                    )
                }
            }
            if (logOutIcon) {
                IconButton(
                    modifier = Modifier.size(48.dp),
                    onClick = logOut,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout_icon),
                        contentDescription = null,
                        tint = Color.Unspecified // Ensures the original vector color is used
                    )
                }
            }
        }
        Image(
            modifier = Modifier
                .padding(top = 28.dp)
                .align(Alignment.Center),
            painter = painterResource(R.drawable.icon),
            contentDescription = stringResource(R.string.icon)
        )
    }

    Spacer(modifier = Modifier.height(20.dp))

    TopTexts(
        modifier = Modifier.fillMaxWidth(),
        firstText = firstText,
        secondText = secondText
    )

    Spacer(modifier = Modifier.height(36.dp))
}

sealed interface PinOptions {
    data object IsCreatePin : PinOptions
    data object IsRepeatPin : PinOptions
    data object IsPromptPin : PinOptions
}

@Composable
fun KeyBoardItem(
    text: String,
    isEnabled: Boolean,
    pinOptions: PinOptions,
    onCreatePinActions: (CreatePinActions) -> Unit = {},
    onRepeatPinActions: (RepeatPinActions) -> Unit = {},
    onPromptPinActions: (PromptPinActions) -> Unit = {},
) {
    if (text.isNotEmpty() && text != "remove") {
        //aspectRatio(1f) here means the height will take the same size as width
        TextButton(
            modifier = Modifier
                .aspectRatio(1f),
            shape = RoundedCornerShape(32.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Schemes_Keyboard_Primary,
                disabledContainerColor = Schemes_Keyboard_Primary.copy(alpha = 0.3f),
                contentColor = Schemes_Keyboard_OnPrimary,
                disabledContentColor = Schemes_Keyboard_OnPrimary.copy(alpha = 0.3f),
            ),
            enabled = isEnabled,
            onClick = {
                when (pinOptions) {
                    PinOptions.IsCreatePin -> onCreatePinActions(CreatePinActions.UpdatePin(text))
                    PinOptions.IsRepeatPin -> onRepeatPinActions(RepeatPinActions.UpdatePin(text))
                    PinOptions.IsPromptPin -> onPromptPinActions(PromptPinActions.UpdatePin(text))
                }
            },
        ) {
            Text(
                modifier = Modifier,
                text = text,
                style = Typography.headlineLarge.copy(color = LocalContentColor.current)
            )
        }
    } else if (text == "remove") {
        TextButton(
            modifier = Modifier
                .aspectRatio(1f),
            shape = RoundedCornerShape(32.dp),
            enabled = isEnabled,
            colors = ButtonDefaults.buttonColors(
                disabledContainerColor = Schemes_Keyboard_Primary.copy(alpha = 0.6f),
                containerColor = Schemes_Keyboard_Primary.copy(alpha = 0.3f),
                disabledContentColor = Schemes_Keyboard_OnPrimary.copy(alpha = 0.3f),
                contentColor = Schemes_Keyboard_OnPrimary
            ),
            onClick = {
                when (pinOptions) {
                    PinOptions.IsCreatePin -> onCreatePinActions(CreatePinActions.UpdatePin(text))
                    PinOptions.IsPromptPin -> onRepeatPinActions(RepeatPinActions.UpdatePin(text))
                    PinOptions.IsRepeatPin -> onPromptPinActions(PromptPinActions.UpdatePin(text))
                }
            },
        ) {
            Icon(
                modifier = Modifier,
                painter = painterResource(R.drawable.remove),
                contentDescription = null,
                tint = LocalContentColor.current
            )
        }
    }
}

@Composable
fun TopTexts(
    modifier: Modifier,
    firstText: String,
    secondText: String,
    textAlign: TextAlign = TextAlign.Center,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            modifier = Modifier,
            text = firstText,
            style = Typography.headlineMedium,
            textAlign = textAlign
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier,
            text = secondText,
            style = Typography.bodyMedium,
            textAlign = textAlign
        )
    }
}

@Composable
fun Banner(bannerText: String) {
    Text(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(Schemes_Error)
            .padding(vertical = 14.dp),
        text = bannerText,
        style = Typography.labelMedium,
        textAlign = TextAlign.Center
    )
}

@Composable
fun BannerHandler(onActions: (SharedActions) -> Unit, lifecycleOwner: LifecycleOwner) {
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_PAUSE) {
                onActions(SharedActions.HideBanner)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}
