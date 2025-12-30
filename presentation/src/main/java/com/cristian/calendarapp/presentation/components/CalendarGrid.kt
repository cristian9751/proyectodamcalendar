package com.cristian.calendarapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cristian.calendarapp.presentation.model.CalendarEvent
import com.cristian.calendarapp.presentation.utils.toLocalDate
import com.kizitonwose.calendar.compose.CalendarState
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import java.time.DayOfWeek
import java.time.YearMonth

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
    var targetDate by mutableStateOf(System.currentTimeMillis().toLocalDate())
    val eventDates = remember(events.size) {
        events.map { it.timestamp.toLocalDate() }
    }
    val currentMonth = remember { YearMonth.from(targetDate) }

    val calendarState: CalendarState = rememberCalendarState(
        startMonth = currentMonth,
        firstDayOfWeek = DayOfWeek.MONDAY,
        endMonth = currentMonth,
        firstVisibleMonth = currentMonth
    )

    HorizontalCalendar(
        state = calendarState,
        dayContent = { day ->
            val isSelected = day.date == targetDate
            val hasEvent = eventDates.contains(day.date)
            Box(
                modifier = Modifier
                    .aspectRatio(1f)
                    .background(if(isSelected) Color.Gray else if(hasEvent) Color.Blue else Color.Transparent),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.date.dayOfMonth.toString(),
                    color = if (isSelected) Color.White else Color.Black,
                    modifier = Modifier.clickable{
                        targetDate = day.date
                    }
                )
            }

        }
    )
}

