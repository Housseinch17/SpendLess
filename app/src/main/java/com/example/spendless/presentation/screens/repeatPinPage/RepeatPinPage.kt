package com.example.spendless.presentation.screens.repeatPinPage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.spendless.R
import com.example.spendless.presentation.theme.Light_OnBackground
import com.example.spendless.presentation.theme.Schemes_Primary
import com.example.spendless.presentation.util.KeyBoardItem
import com.example.spendless.presentation.util.PinOptions
import com.example.spendless.presentation.util.SharedComponent
import com.example.spendless.presentation.util.Utils

@Composable
fun RepeatPinPage(
    modifier: Modifier,
    state: RepeatPinUiState,
    onActions: (RepeatPinActions) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SharedComponent(
            backButton = true,
            firstText = stringResource(R.string.repeat_pin),
            secondText = stringResource(R.string.enter_pin),
            navigateBack = { onActions(RepeatPinActions.NavigateBack) }
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(state.ellipseList) { item ->
                Icon(
                    painter = painterResource(R.drawable.ellipse_icon), contentDescription = null,
                    tint = if (item) {
                        Schemes_Primary
                    } else Light_OnBackground
                )
                Spacer(modifier = Modifier.width(10.dp))
            }
        }

        Spacer(modifier = Modifier.padding(top = 32.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(Utils.keyboardSet) { keyboardItem ->
                KeyBoardItem(
                    isEnabled = state.buttonEnabled,
                    pinOptions = PinOptions.IsRepeatPin,
                    text = keyboardItem,
                    onRepeatPinActions = onActions,
                )
            }
        }
    }
}