package com.cristian.calendarapp.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.components.AppScaffold
import com.cristian.calendarapp.presentation.viewmodel.ProfileViewModel
import com.cristian.calendarapp.presentation.R
import com.cristian.calendarapp.presentation.components.ProfileField

@Composable
fun ProfileScreen(navController: NavController) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val uiState by profileViewModel.uiState.observeAsState(initial = UiState.Idle)
    val firstname by profileViewModel.firstname.observeAsState(initial = "")
    val lastname by profileViewModel.lastname.observeAsState(initial = "")
    val email by profileViewModel.email.observeAsState(initial = "")
    val role by profileViewModel.role.observeAsState(initial = "")


    AppScaffold(
        uiState = uiState,
        navController = navController,
        title = "$firstname $lastname",
        showProfileIcon = false
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimary),
                contentAlignment = Alignment.Center
            ) {

            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "$firstname $lastname",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = role,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ProfileField(label = stringResource(R.string.firstname_label), value = firstname)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)
                    ProfileField(label = stringResource(R.string.lastname_label), value = lastname)
                    HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 0.5.dp)
                    ProfileField(label = stringResource(R.string.email_label), value = email)
                }
            }
        }
    }
}
