package com.cristian.calendarapp.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cristian.calendarapp.presentation.R

@Composable
fun IconWithText() {
    Icon(
        painter = painterResource(id = android.R.drawable.ic_menu_today), // Placeholder simple
        contentDescription = "App Icon",
        modifier = Modifier
            .size(64.dp)
            .padding(8.dp)
    )



    Text(
        text = stringResource(R.string.app_name),
        fontSize = 32.sp,
        fontWeight = FontWeight.Bold,
    )
    Text(
        text = stringResource(R.string.slogan),
        fontSize = 14.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}