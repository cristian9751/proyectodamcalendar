package com.cristian.calendarapp.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
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
import com.cristian.calendarapp.presentation.components.CardForm
import com.cristian.calendarapp.presentation.components.IconWithText
import com.cristian.calendarapp.presentation.viewmodel.AuthViewModel
import com.cristian.calendarapp.presentation.R
import com.cristian.calendarapp.presentation.Routes
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.components.EmailInputField
import com.cristian.calendarapp.presentation.components.ErrorText
import com.cristian.calendarapp.presentation.components.NameField
import com.cristian.calendarapp.presentation.components.PasswordInputField

@Composable
fun SignUpScreen(navController: NavController) {

    var repeatPassword by remember { mutableStateOf("") }
    var valid by remember { mutableStateOf(false) }

    val authViewModel : AuthViewModel = hiltViewModel()

    val email by  authViewModel.email.observeAsState(initial = "")
    val password by authViewModel.password.observeAsState(initial = "")
    val firstname by authViewModel.firstname.observeAsState(initial = "")
    val lastname by authViewModel.lastname.observeAsState(initial = "")
    val uiState by authViewModel.uiState.observeAsState(initial = UiState.Idle)

    val isSubmitButtonVisible by remember {
        derivedStateOf {
            valid && email.isNotEmpty() && password.isNotEmpty() && firstname.isNotEmpty()
                    && lastname.isNotEmpty() && password == repeatPassword
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconWithText()
            ErrorText(uiState)
            CardForm(
                submitButtonIsEnabled = isSubmitButtonVisible,
                title = stringResource(R.string.registration),
                subtitle = stringResource(R.string.registration_subtitle),
                submitButtonLabel = stringResource(R.string.registration),
                footer = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.alreadyaccount_question),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            modifier = Modifier.clickable(
                                enabled = true,
                                onClick = {
                                    navController.navigate(Routes.LoginScreen.route)
                                }
                            ),
                            text = stringResource(R.string.loginhere_link),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                onSubmit = {
                    authViewModel.signUp()
                },

                ) {
                EmailInputField(email ) {
                        it, isEmailValid ->
                    authViewModel.setEmail(it)
                    valid = isEmailValid
                }

                NameField(
                    name = firstname,
                    label = stringResource(R.string.firstname_label),
                    placeholder = stringResource(R.string.firstname_placeholder)
                ) {
                    authViewModel.setFirstname(it)
                }

                NameField(
                    name = lastname,
                    label = stringResource(R.string.lastname_label),
                    placeholder = stringResource(R.string.lastname_placeholder)
                ) {
                    authViewModel.setLastName(it)
                }

                PasswordInputField(password) {
                        it, isPasswordValid ->
                    authViewModel.setPassword(it)
                    valid = isPasswordValid
                }

                PasswordInputField(
                    showValidationErrorMessage = false,
                    password = repeatPassword,
                ) {
                        it, _ ->
                    repeatPassword = it
                }
                if((repeatPassword.isNotEmpty()) && password != repeatPassword) {
                    Text(
                        text = stringResource(R.string.password_notmatch),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

