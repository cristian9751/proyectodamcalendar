package com.cristian.calendarapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cristian.calendarapp.presentation.model.CalendarEvent
@Composable
fun DayNumberCircle(dayNumber: Int) {
    Text(
        text = dayNumber.toString(),
        modifier = Modifier
            .padding(vertical = 4.dp),
        color = MaterialTheme.colorScheme.onSurface,
        fontSize = 12.sp,
        fontWeight = FontWeight.Normal
    )
}
@Composable
fun CalendarGrid(
    events : List<CalendarEvent> = emptyList()
) {
    val daysOfWeek = listOf("Dom", "Lun", "Mar", "Mié", "Jue", "Vie", "Sáb")
    val startOffset = 1

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        border = AssistChipDefaults.assistChipBorder(
            enabled = true,
            borderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
    ) {
        Column {
            Row(modifier = Modifier.fillMaxWidth()) {
                daysOfWeek.forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier
                            .weight(1f)
                            .padding(vertical = 12.dp),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }

            for(row in 0 until 5) {
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                Row(modifier = Modifier.height(100.dp)) {
                    for (col in 0 until 7) {
                        val cellIndex = row * 7 + col
                        val dayEvents = events.filter { it.day == cellIndex }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .padding(2.dp),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            if (cellIndex in 1..31) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    DayNumberCircle(
                                        dayNumber = cellIndex
                                    )
                                    dayEvents.take(2).forEach { event ->
                                        Text(text = event.title)
                                    }

                                    if(dayEvents.size > 2) {
                                        Text(
                                            text = "+${dayEvents.size - 2}",
                                            fontSize = 10.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            }
                        }
                        if(col < 6) {
                            VerticalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                        }
                    }
                }
            }

            val totalCells = 35
            val rows = totalCells / 7

            for (row in 0 until rows) {
                HorizontalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                Row(modifier = Modifier.height(80.dp)) {
                    for (col in 0 until 7) {
                        val cellIndex = row * 7 + col
                        val dayNumber = cellIndex - startOffset + 1

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            contentAlignment = Alignment.TopCenter
                        ) {
                            if (dayNumber in 1..31) {
                                val isToday = dayNumber == 19

                                Text(
                                    text = dayNumber.toString(),
                                    modifier = Modifier
                                        .padding(top = 8.dp)
                                        .then(
                                            if (isToday) Modifier
                                                .size(32.dp)
                                                .background(MaterialTheme.colorScheme.primary, CircleShape)
                                                .wrapContentSize(Alignment.Center)
                                            else Modifier
                                        ),
                                    color = if (isToday) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.onSurface,
                                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal
                                )
                            }
                        }
                        if (col < 6) {
                            VerticalDivider(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f))
                        }
                    }
                }
            }
        }
    }
}


