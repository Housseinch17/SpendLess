package com.example.spendless.presentation.screens.onboardingPreferencesPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.spendless.R
import com.example.spendless.data.model.Currency
import com.example.spendless.presentation.theme.Light_PrimaryContainer
import com.example.spendless.presentation.theme.Schemes_Error
import com.example.spendless.presentation.theme.Schemes_OnPrimary
import com.example.spendless.presentation.theme.Schemes_OnPrimary_Fixed
import com.example.spendless.presentation.theme.Schemes_OnSurface
import com.example.spendless.presentation.theme.Schemes_OnSurface_Variant
import com.example.spendless.presentation.theme.Schemes_Primary
import com.example.spendless.presentation.theme.Typography
import com.example.spendless.presentation.util.TopTexts
import com.example.spendless.presentation.util.Utils.currencyList
import com.example.spendless.presentation.util.Utils.decimalSeparatorList
import com.example.spendless.presentation.util.Utils.expensesList
import com.example.spendless.presentation.util.Utils.thousandsSeparator

@ExperimentalMaterial3Api
@Composable
fun OnboardingPreferencesPage(
    modifier: Modifier,
    state: OnboardingPreferencesUiState,
    onActions: (OnboardingPreferencesActions) -> Unit,
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        IconButton(
            modifier = Modifier.size(40.dp),
            onClick = { onActions(OnboardingPreferencesActions.NavigateBack) },
        ) {
            Icon(
                modifier = Modifier,
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = null,
                tint = Schemes_OnSurface
            )

        }

        Spacer(modifier = Modifier.height(20.dp))

        TopTexts(
            modifier = Modifier.fillMaxWidth(),
            firstText = stringResource(R.string.set_spendLess_to_your_preferences),
            secondText = stringResource(R.string.you_can_change_____time_in_settings),
            textAlign = TextAlign.Start,
            horizontalAlignment = Alignment.Start
        )

        Spacer(modifier = Modifier.height(24.dp))

        PreferencesFormatPrice(
            preferencesValue = state.formattedPrice
        )

        Spacer(modifier = Modifier.height(20.dp))

        PreferencesItem(
            text = stringResource(R.string.expenses_format),
            list = expensesList,
            selectedItem = state.preferencesFormat.expensesIsNegative,
            onClick = { expenses ->
                onActions(OnboardingPreferencesActions.UpdateExpenses(expensesIsNegative = expenses))
            }
        )

        Spinner(
            currencySelected = state.preferencesFormat.currency,
            isExpanded = state.isExpanded,
            currencyList = currencyList,
            onCurrencySelect = { currency ->
                onActions(OnboardingPreferencesActions.UpdateCurrency(currency = currency))
            },
            onExpand = {
                onActions(OnboardingPreferencesActions.OnExpand)
            }
        )

        PreferencesItem(
            text = stringResource(R.string.decimal_separator),
            list = decimalSeparatorList,
            selectedItem = state.preferencesFormat.decimalSeparator,
            onClick = { decimalSeparator ->
                onActions(OnboardingPreferencesActions.UpdateDecimalSeparator(decimalSeparator = decimalSeparator))
            }
        )

        PreferencesItem(
            text = stringResource(R.string.thousands_separator),
            list = thousandsSeparator,
            selectedItem = state.preferencesFormat.thousandsSeparator,
            onClick = { thousandsSeparator ->
                onActions(OnboardingPreferencesActions.UpdateThousandsSeparator(thousandsSeparator = thousandsSeparator))
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onActions(OnboardingPreferencesActions.StartTracking)
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Schemes_Primary,
                contentColor = Schemes_OnPrimary,
                disabledContainerColor = Schemes_Error,
                disabledContentColor = Schemes_OnPrimary
            ),
            enabled = !(state.hasSameOperator)
            ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = stringResource(R.string.start_tracking),
                style = Typography.titleMedium.copy(color = LocalContentColor.current)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@ExperimentalMaterial3Api
@Composable
fun Spinner(
    currencySelected: Currency,
    isExpanded: Boolean,
    currencyList: List<Currency>,
    onCurrencySelect: (Currency) -> Unit,
    onExpand: () -> Unit,
) {
    Column(
        modifier = Modifier,
    ) {
        Text(
            text = stringResource(R.string.currency),
            style = Typography.labelSmall
        )

        Spacer(modifier = Modifier.height(4.dp))

        Column(
            modifier = Modifier,
        ) {
            ExposedDropdownMenuBox(
                modifier = Modifier,
                expanded = isExpanded,
                onExpandedChange = {
                    onExpand()
                }
            ) {
                SpinnerSelectedItem(
                    modifier = Modifier.menuAnchor(
                        type = MenuAnchorType.PrimaryNotEditable,
                        enabled = true
                    ),
                    currencySelected = currencySelected,
                    expanded = isExpanded
                )
                ExposedDropdownMenu(
                    modifier = Modifier
                        .fillMaxWidth(),
                    matchTextFieldWidth = false,
                    shape = RoundedCornerShape(16.dp),
                    containerColor = Schemes_OnPrimary,
                    expanded = isExpanded,
                    onDismissRequest = {
                        onExpand()
                    },
                ) {
                    SpinnerItemList(
                        currencyList = currencyList,
                        selectedCurrency = currencySelected,
                        onCurrencySelect = onCurrencySelect,
                        updateExpanded = {
                            onExpand()
                        }
                    )
                }

            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerSelectedItem(
    modifier: Modifier,
    currencySelected: Currency,
    expanded: Boolean,
) {
    TextField(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)),
        value = currencySelected.name,
        onValueChange = {},
        readOnly = true,
        textStyle = Typography.bodyMedium.copy(color = Schemes_OnSurface_Variant),
        leadingIcon = {
            Text(
                modifier = Modifier,
                text = currencySelected.symbol,
                style = Typography.labelMedium.copy(
                    color = Schemes_OnSurface
                )
            )
        },
        trailingIcon = {
            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
        },
        colors = TextFieldDefaults.colors().copy(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = Schemes_OnPrimary,
            unfocusedContainerColor = Schemes_OnPrimary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerItemList(
    currencyList: List<Currency>,
    selectedCurrency: Currency,
    onCurrencySelect: (Currency) -> Unit,
    updateExpanded: () -> Unit,
) {
    currencyList.forEachIndexed { index, currency ->
        DropdownMenuItem(
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
            text = {
                Text(
                    modifier = Modifier,
                    text = currency.name,
                    style = Typography.bodyMedium.copy(color = Schemes_OnSurface_Variant),
                )
            },
            onClick = {
                onCurrencySelect(currency)
                updateExpanded()
            },
            leadingIcon = {
                Text(
                    modifier = Modifier,
                    text = currency.symbol,
                    style = Typography.labelMedium.copy(
                        color = Schemes_OnSurface
                    )
                )
            },
            trailingIcon = {
                if (currency == selectedCurrency) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Schemes_Primary
                    )
                }
            },
        )
    }
}

@Composable
fun PreferencesFormatPrice(
    preferencesValue: String,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(color = Schemes_OnPrimary)
            .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier,
            text = preferencesValue,
            style = Typography.headlineMedium,
        )
        Spacer(modifier = Modifier.height(2.dp))

        Text(
            modifier = Modifier,
            text = stringResource(R.string.spend_this_month),
            style = Typography.bodyMedium,
        )

    }
}


@Composable
fun <T> PreferencesItem(
    text: String,
    list: List<T>,
    selectedItem: T,
    onClick: (T) -> Unit,
) {
    Text(
        text = text,
        style = Typography.labelSmall
    )

    Spacer(modifier = Modifier.height(4.dp))

    LazyVerticalGrid(
        columns = GridCells.Fixed(list.size),
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 100.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(color = Light_PrimaryContainer.copy(alpha = 0.08f)),
        horizontalArrangement = Arrangement.Center,
        contentPadding = PaddingValues(4.dp)
    ) {
        items(list) { item ->
            val isSelected = item == selectedItem
            PreferencesTextButtons(
                textButton = when (item) {
                    true -> "-$10"
                    false -> "($10)"
                    else -> item.toString()
                },
                isTextButtonSelected = isSelected,
                onTextButtonClick = {
                    onClick(item)
                }
            )
        }
    }

    Spacer(modifier = Modifier.height(16.dp))

}

@Composable
fun PreferencesTextButtons(
    textButton: String,
    isTextButtonSelected: Boolean,
    onTextButtonClick: () -> Unit,
) {
    TextButton(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(
                color = if (isTextButtonSelected) Schemes_OnPrimary else Color.Transparent
            ),
        onClick = onTextButtonClick,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Text(
            modifier = Modifier.fillMaxSize(),
            text = textButton,
            style = Typography.titleLarge.copy(color = if (isTextButtonSelected) Schemes_OnSurface else Schemes_OnPrimary_Fixed),
            textAlign = TextAlign.Center
        )
    }
}