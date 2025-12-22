package com.cristian.calendarapp.presentation.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.components.AddEventPopUp
import com.cristian.calendarapp.presentation.components.AppScaffold
import com.cristian.calendarapp.presentation.components.CalendarGrid
import com.cristian.calendarapp.presentation.model.CalendarEvent
import com.cristian.calendarapp.presentation.viewmodel.CalendarViewModel


@Composable
fun CalendarScreen(navController : NavController) {
    val calendarViewModel : CalendarViewModel = hiltViewModel()
    val uiState by calendarViewModel.uiState.observeAsState(initial = UiState.Idle)
    val events by calendarViewModel.events.observeAsState(initial = emptyList())
    var newButtonClicked by remember { mutableStateOf(false) }
    if(newButtonClicked) {
        AddEventPopUp() { title, desc, date ->
            calendarViewModel.createEvent(
                event = CalendarEvent(
                    title = title,
                    description = desc,
                    day = date.split("/")[0].toInt(),
                    teamId = calendarViewModel.teamId
                )

            )
        }
    }
    AppScaffold(
        uiState = uiState,
        title = "Calendario",
        navController = navController,
        showProfileIcon = false,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(
                        onClick = {
                            newButtonClicked = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(Modifier.width(4.dp))
                        Text("Nuevo Evento")
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = {
                    //todo
                },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Icon(Icons.Default.PersonAdd, contentDescription = null)
                Spacer(Modifier.width(4.dp))
                Text("Añadir usuario")
            }

            Spacer(modifier = Modifier.height(24.dp))

            CalendarGrid(events)
        }
    }

}

