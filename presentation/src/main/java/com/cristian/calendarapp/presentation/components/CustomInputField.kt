package com.cristian.calendarapp.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun CustomInputField(
    visualTransformation: VisualTransformation = VisualTransformation.None,
    value : String,
    regex : Regex? = null,
    label : String,
    placeholder : String,
    showValidationErrorMessage : Boolean  = true,
    errorText : String = "",
    leadingIcon: (@Composable (() -> Unit))? = null,
    onValueChangeCallback : (String, Boolean) -> Unit
) {
    var isValid by remember { mutableStateOf(true) }
    OutlinedTextField(
        visualTransformation = visualTransformation,
        value = value,
        onValueChange = { newValue : String ->
            isValid = if(newValue.isNotEmpty() && regex !== null) newValue.matches(regex) else true
            onValueChangeCallback(newValue, isValid)
        },
        modifier = Modifier.fillMaxWidth(),
        isError = !isValid,
        placeholder = {
            Text(text = placeholder)
        },
        label = {
            Text(text = label)
        },
        leadingIcon = leadingIcon,
        shape = RoundedCornerShape(10.dp)

    )

    if(showValidationErrorMessage && !isValid) {
        Text(
            text = errorText,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall
        )
    }
}