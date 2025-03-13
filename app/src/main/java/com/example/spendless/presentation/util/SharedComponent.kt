package com.example.spendless.presentation.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.example.spendless.R
import com.example.spendless.presentation.screens.shared.SharedActions
import com.example.spendless.presentation.theme.Schemes_Error
import com.example.spendless.presentation.theme.Schemes_OnSurface
import com.example.spendless.presentation.theme.Typography


@Composable
fun SharedComponent(
    showIcon: Boolean = false,
    logOutIcon: Boolean = false,
    firstText: String,
    secondText: String,
    navigateBack: () -> Unit = {},
    logOut: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 36.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopStart)
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (showIcon) {
                IconButton(
                    modifier = Modifier,
                    onClick = navigateBack,
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = null,
                        tint = Schemes_OnSurface
                    )
                }
            }
            if (logOutIcon) {
                IconButton(
                    modifier = Modifier,
                    onClick = logOut,
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.logout_icon),
                        contentDescription = null,
                        tint = Schemes_OnSurface
                    )
                }
            }
        }
        Image(
            modifier = Modifier
                .padding(top = 8.dp)
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

@Composable
fun TopTexts(modifier: Modifier, firstText: String, secondText: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = firstText,
            style = Typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier,
            text = secondText,
            style = Typography.bodyMedium,
            textAlign = TextAlign.Center
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
