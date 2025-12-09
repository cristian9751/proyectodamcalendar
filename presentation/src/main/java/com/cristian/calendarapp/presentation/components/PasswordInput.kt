package com.cristian.calendarapp.presentation.components


import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.cristian.calendarapp.presentation.R

@Composable
fun PasswordInputField(
    password : String,
    showValidationErrorMessage : Boolean = true,
    onValueChange : (String, Boolean) -> Unit
) {
    CustomInputField(
        visualTransformation = PasswordVisualTransformation(mask = '*'),
        value = password,
        regex = Regex("""^(?=.*[a-z])(?=.*[A-Z])(?=.*[_\-@])[A-Za-z0-9_\-@]{8,16}$"""),
        label = stringResource(R.string.password_label),
        placeholder = stringResource(R.string.password_placeholder),
        showValidationErrorMessage = showValidationErrorMessage,
        errorText =  stringResource(R.string.password_invalid) ,
        onValueChangeCallback = onValueChange,
        leadingIcon = {
            Icon(
                Icons.Default.Key,
                contentDescription = "${stringResource(R.string.password_label)} ${stringResource(R.string.icon_label)}"
            )
        }
    )
}