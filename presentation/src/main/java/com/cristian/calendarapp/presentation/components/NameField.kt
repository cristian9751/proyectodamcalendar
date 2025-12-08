package com.cristian.calendarapp.presentation.components
import androidx.compose.material.icons.Icons
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.cristian.calendarapp.presentation.R


@Composable
fun NameField(
    name: String,
    label: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = name,
        onValueChange = onValueChange,
        label = { Text(label) },
        leadingIcon = {
            Icon(
                Icons.Default.Person,
                contentDescription = "$label ${stringResource(R.string.icon_label)}"
            )
        },
        placeholder = {
            Text(text = placeholder)
        },
        modifier = Modifier.fillMaxWidth()
    )
}