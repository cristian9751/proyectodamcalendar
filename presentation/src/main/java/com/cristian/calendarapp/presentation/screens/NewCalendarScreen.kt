package com.cristian.calendarapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.components.AppScaffold
import com.cristian.calendarapp.presentation.components.CustomInputField
import com.cristian.calendarapp.presentation.utils.sharedViewModelOnSubGraph
import com.cristian.calendarapp.presentation.viewmodel.TeamsViewModel
import com.cristian.calendarapp.presentation.R
import com.cristian.calendarapp.presentation.components.ErrorText

@Composable
fun NewTeamScreen(navController: NavController) {
    val teamViewModel : TeamsViewModel = navController.sharedViewModelOnSubGraph<TeamsViewModel>()
    val uiState by teamViewModel.uiState.observeAsState(initial = UiState.Idle)
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val isSaveButtonEnabled by remember {
        derivedStateOf {
            name.isNotEmpty() && description.isNotEmpty()
        }
    }

    LaunchedEffect(uiState) {
        if(uiState is UiState.Success) {
            navController.navigateUp()
        }
    }
    AppScaffold(
        uiState = uiState,
        title = stringResource(R.string.new_calendar)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            ErrorText(uiState)

            CustomInputField(
                value = name,
                onValueChangeCallback = { newName, _ ->
                    name = newName
                },
                label = stringResource(R.string.calendar_name_label),
                placeholder = stringResource(R.string.calendar_name_placeholder)
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomInputField (
                value = description,
                onValueChangeCallback = {  newDescription,  _ ->
                    description = newDescription
                },
                label = stringResource(R.string.calendar_description_label),
                placeholder = stringResource(R.string.calendar_description_placeholder)
            )
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Button(
                    onClick = {
                        navController.navigateUp()
                        teamViewModel.setUiState(UiState.Idle)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.cancel_text), color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = {
                        teamViewModel.addTeam(name, description)
                    },
                    enabled = isSaveButtonEnabled,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(stringResource(R.string.create_calendar_text), color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

}

