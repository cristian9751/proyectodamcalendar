package com.cristian.calendarapp.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.components.AppScaffold
import com.cristian.calendarapp.presentation.components.CustomInputField
import com.cristian.calendarapp.presentation.utils.sharedViewModelOnSubGraph
import com.cristian.calendarapp.presentation.viewmodel.TeamsViewModel


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


    AppScaffold(
        uiState = uiState,
        title = "New Team"
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomInputField(
                value = name,
                onValueChangeCallback = { newName, _ ->
                    name = newName
                },
                label = "Nombre del Equipo / Calendario",
                placeholder = "Ej: Equipo de Marketing, Proyecto X"
            )
            Spacer(modifier = Modifier.height(16.dp))

            CustomInputField (
                value = description,
                onValueChangeCallback = {  newDescription,  _ ->
                    description = newDescription
                },
                label = "Descripción",
                placeholder = "Propósito, objetivos o miembros principales"
            )
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Botón de Cancelar
                Button(
                    onClick = {
                        navController.navigateUp()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.background),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Cancelar", color = Color.White, fontWeight = FontWeight.SemiBold)
                }

                // Botón de Guardar
                Button(
                    onClick = {
                        teamViewModel.addTeam(name, description)
                    },
                    enabled = isSaveButtonEnabled, // Control de habilitación
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ),
                    contentPadding = PaddingValues(vertical = 12.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Crear Equipo", fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }

}

