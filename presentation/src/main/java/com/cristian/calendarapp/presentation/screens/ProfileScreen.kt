package com.cristian.calendarapp.presentation.screens

import RoleDropDownMenu
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristian.calendarapp.domain.ROLE
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.components.AppScaffold
import com.cristian.calendarapp.presentation.viewmodel.ProfileViewModel
import com.cristian.calendarapp.presentation.R
import com.cristian.calendarapp.presentation.components.EmailInputField
import com.cristian.calendarapp.presentation.components.ErrorText
import com.cristian.calendarapp.presentation.components.NameField

@Composable
fun ProfileScreen(navController: NavController) {
    val profileViewModel: ProfileViewModel = hiltViewModel()
    val uiState by profileViewModel.uiState.observeAsState(initial = UiState.Idle)
    val firstname by profileViewModel.firstname.observeAsState(initial = "")
    val lastname by profileViewModel.lastname.observeAsState(initial = "")
    val email by profileViewModel.email.observeAsState(initial = "")
    val role by profileViewModel.role.observeAsState(initial = "")
    var isSubmitButtonEnabled by remember { mutableStateOf(true) }


    AppScaffold(
        uiState = uiState,
        navController = navController,
        title = "$firstname $lastname",
        showProfileIcon = false
    ) {
        LaunchedEffect(uiState) {
            if(uiState is UiState.Success) {
                navController.navigateUp()
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(16.dp))

            ErrorText(uiState)
            Text(
                text = "$firstname $lastname",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    RoleDropDownMenu(
                        selectedRole = role,
                        onMemberSelected = {
                            profileViewModel.setRole(ROLE.MEMBER.toString())
                        },
                        onModeratorSelected = {
                            profileViewModel.setRole(ROLE.MODERATOR.toString())
                        },
                        onAdminSelected = {
                            profileViewModel.setRole(ROLE.ADMIN.toString())
                        }
                    )
                    NameField(
                        name = firstname,
                        label = stringResource(R.string.firstname_label),
                        placeholder = stringResource(R.string.firstname_placeholder)
                    ) {
                        profileViewModel.setFirstName(it)

                    }

                    NameField(
                        name = lastname,
                        label = stringResource(R.string.lastname_label),
                        placeholder = stringResource(R.string.lastname_placeholder)
                    )
                    {
                        profileViewModel.setLastName(it)

                    }

                    EmailInputField(
                        email = email,
                        showValidationErrorMessage = true,
                        onValueChange = { newEmail, isValid ->
                            profileViewModel.setEmail(newEmail)
                            isSubmitButtonEnabled = isValid
                        }
                    )





                    Button(
                        enabled = isSubmitButtonEnabled,
                        onClick =  {
                            profileViewModel.updateProfile()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(stringResource(R.string.update), fontSize = 16.sp)
                    }
                }
            }
        }
    }
}
