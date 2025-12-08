package com.cristian.calendarapp.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.cristian.calendarapp.presentation.R

@Composable
fun EmailInputField(
    email : String,
    showValidationErrorMessage : Boolean = true,
    onValueChange : (String, Boolean) -> Unit
) {
    CustomInputField(
        value = email,
        regex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"),
        label = stringResource(R.string.email_label),
        placeholder = stringResource(R.string.email_placeholder),
        showValidationErrorMessage = showValidationErrorMessage,
        errorText =  stringResource(R.string.email_invalid) ,
        onValueChangeCallback = onValueChange,
        leadingIcon = {
            Icon(
                Icons.Default.MailOutline,
                contentDescription = "${stringResource(R.string.email_label)} ${stringResource(R.string.icon_label)}"
            )
        }
    )
}