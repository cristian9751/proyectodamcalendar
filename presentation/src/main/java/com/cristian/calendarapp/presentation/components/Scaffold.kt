package com.cristian.calendarapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cristian.calendarapp.presentation.UiState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  AppScaffold(
    uiState : UiState,
    title :  String,
    content : @Composable (PaddingValues) -> Unit
) {
    if(uiState is UiState.Loading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.CenterStart,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CircularProgressIndicator()
            }
        }
    } else {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = title,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Bold
                        )
                    }
                )
            },
            content = { paddingValues ->
                content(paddingValues)

            }
        )
    }
}