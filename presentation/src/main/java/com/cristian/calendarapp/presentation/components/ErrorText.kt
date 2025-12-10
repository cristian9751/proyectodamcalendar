package com.cristian.calendarapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cristian.calendarapp.presentation.UiState

@Composable
fun ErrorText(uiState: UiState) {
    if(uiState is UiState.Error) {
        Text(
            text = stringResource(uiState.errorResourceId),
            color = MaterialTheme.colorScheme.error
        )
    }
}