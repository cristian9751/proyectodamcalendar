package com.cristian.calendarapp.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
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
import androidx.compose.runtime.LaunchedEffect
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
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.components.CardForm
import com.cristian.calendarapp.presentation.components.IconWithText
import com.cristian.calendarapp.presentation.viewmodel.AuthViewModel
import com.cristian.calendarapp.presentation.R
import com.cristian.calendarapp.presentation.Routes
import com.cristian.calendarapp.presentation.components.EmailInputField
import com.cristian.calendarapp.presentation.components.ErrorText
import com.cristian.calendarapp.presentation.components.PasswordInputField

@Composable
fun LoginScreen(navController: NavController) {
    val authViewModel : AuthViewModel = hiltViewModel()
    val email by  authViewModel.email.observeAsState(initial = "")
    val password by authViewModel.password.observeAsState(initial = "")
    val uiState by authViewModel.uiState.observeAsState(initial = UiState.Idle)
    var valid by remember { mutableStateOf(false) }
    val submitButtonIsVisible by remember {
        derivedStateOf {
            valid && email.isNotEmpty() && password.isNotEmpty()
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.CenterStart,
    ) {



        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            IconWithText()
            ErrorText(uiState)
            LaunchedEffect(key1 = uiState) {
                if(uiState is UiState.Success<*>) {
                    Log.d("AUTHENTICATION", "Se ha inciado sesion correctamente")
                }
            }

            CardForm(
                submitButtonIsEnabled = submitButtonIsVisible,
                title = stringResource(R.string.login),
                subtitle = stringResource(R.string.login_subtitle),
                submitButtonLabel = stringResource(R.string.login),
                footer = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.noaccount_question),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            modifier = Modifier.clickable(
                                enabled = true,
                                onClick = {
                                    navController.navigate(Routes.SignUpScreen.route)
                                }
                            ),
                            text = stringResource(R.string.signuphere_link),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                },
                onSubmit = {
                    authViewModel.signIn()
                },
            ) {
                EmailInputField(
                    email = email,
                    showValidationErrorMessage = false
                ) { it, isEmailValid ->
                    authViewModel.setEmail(it)
                    valid = isEmailValid
                }

                PasswordInputField(
                    password = password,
                    showValidationErrorMessage = false
                ) { it, isPasswordValid ->
                    authViewModel.setPassword(it)
                    valid = isPasswordValid

                }
            }
            Text(
                text = stringResource(R.string.tems_accpetance),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                fontSize = 12.sp,
            )
        }
    }
}