package com.cristian.calendarapp.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cristian.calendarapp.presentation.IUiState

@Composable
fun ErrorText(uiState: IUiState) {
    if(uiState.isError()) {
        Text(
            text = stringResource(uiState.errorResourceId),
            color = MaterialTheme.colorScheme.error
        )
    }
}