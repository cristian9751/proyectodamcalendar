package com.cristian.calendarapp.presentation.screens
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristian.calendarapp.presentation.R
import com.cristian.calendarapp.presentation.Routes
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.components.AppScaffold
import com.cristian.calendarapp.presentation.components.CalendarCard
import com.cristian.calendarapp.presentation.components.SearchAndNewCalendar
import com.cristian.calendarapp.presentation.viewmodel.SessionViewModel
import com.cristian.calendarapp.presentation.viewmodel.TeamsViewModel


@Composable
fun HomeScreen(navController : NavController, sessionViewModel: SessionViewModel) {
    val teamsViewModel : TeamsViewModel = hiltViewModel()
    val uiState = teamsViewModel.uiState.observeAsState(initial = UiState.Idle)
    val teams  = teamsViewModel.teams.observeAsState(initial = emptyList())
    var search by remember { mutableStateOf("") }
    AppScaffold(
        uiState = uiState.value,
        title = stringResource(R.string.home_text),
        navController = navController,
        currentUserId =  sessionViewModel.currentUserId.value.toString()
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SearchAndNewCalendar(
                onSearchValueChange = {
                    search = it
                },
                onNewCalendarClicked = {
                    navController.navigate(Routes.NewTeamScreen.route)
                },
                searchValue = search
            )
            if(teams.value.isEmpty()) {
                Text(text = stringResource(R.string.no_calendars))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(
                        teams.value.filter { team ->
                            team.name.contains(search)
                        }
                    ) {
                        Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                            CalendarCard(calendar = it, onCardClick = {})
                        }
                    }
                }
            }
        }

    }

}

