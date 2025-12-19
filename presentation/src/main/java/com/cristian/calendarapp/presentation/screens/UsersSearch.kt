package com.cristian.calendarapp.presentation.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.cristian.calendarapp.presentation.R
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.components.AppScaffold
import com.cristian.calendarapp.presentation.components.ErrorText
import com.cristian.calendarapp.presentation.components.ItemCard
import com.cristian.calendarapp.presentation.components.SearchAndButton
import com.cristian.calendarapp.presentation.utils.Profile
import com.cristian.calendarapp.presentation.viewmodel.UsersViewModel


@Composable
fun UserSearchScreen(navController : NavController) {
    var search by remember { mutableStateOf("") }
    val usersViewModel : UsersViewModel = hiltViewModel()
    val uiState by usersViewModel.uiState.observeAsState(initial = UiState.Idle)
    val users by usersViewModel.users.observeAsState(initial = emptyList())


    AppScaffold(
        uiState = uiState,
        title = stringResource(R.string.home_text),
        navController = navController,
        showProfileIcon = false
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SearchAndButton(
                onSearchValueChange = {
                    search = it
                },
                onButtonClicked = {
                    usersViewModel.searchUser(search)
                },
                searchValue = search,
                buttonLabel = stringResource(R.string.search),
                placeholder = stringResource(R.string.search_label_user),
                buttonIcon = { Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )}
            )
            ErrorText(uiState)

            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(users) {
                    ItemCard(
                        title = it.firstname,
                        subtitle = "${it.firstname} ${it.lastname}",
                        icon = Icons.Default.PersonOutline
                    ) {
                        navController.navigate(Profile(it.id))
                    }

                }

            }

        }



    }
}

