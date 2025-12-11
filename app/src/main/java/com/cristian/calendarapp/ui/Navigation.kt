package com.cristian.calendarapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.cristian.calendarapp.presentation.Routes
import com.cristian.calendarapp.presentation.UiState
import com.cristian.calendarapp.presentation.screens.HomeScreen
import com.cristian.calendarapp.presentation.screens.LoginScreen
import com.cristian.calendarapp.presentation.screens.NewTeamScreen
import com.cristian.calendarapp.presentation.screens.SignUpScreen
import com.cristian.calendarapp.presentation.screens.SplashScreen
import com.cristian.calendarapp.presentation.viewmodel.SessionViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val sessionViewModel : SessionViewModel = hiltViewModel()
    val sessionState by  sessionViewModel.sessionState.observeAsState(initial = UiState.Loading)
    NavHost(
        navController,
        startDestination = getStartDestination(sessionState)
    ) {

        composable(route = Routes.SplashScreen.route) {
            SplashScreen()
        }

        navigation(startDestination = Routes.LoginScreen.route, route = Routes.AuthNav.route) {
            composable(route = Routes.LoginScreen.route) {
                LoginScreen(navController)
            }

            composable(route = Routes.SignUpScreen.route) {
                SignUpScreen(navController)
            }
        }

        navigation(startDestination = Routes.HomeScreen.route, route = Routes.TeamsScreen.route) {
            composable(route = Routes.HomeScreen.route) {
                HomeScreen(navController)
            }

            composable(route = Routes.NewTeamScreen.route) {
                NewTeamScreen(navController)
            }
        }

        navigation(startDestination =  Routes.HomeScreen.route, route = Routes.MainNav.route) {
            composable(route = Routes.ProfileScreen.route) {
                //Profile screen
            }

            composable(route = Routes.CalendarScreen.route) {
                //Calendar screen
            }


        }


    }
}

fun getStartDestination(uiState : UiState) : String {
    return when (uiState) {
        is UiState.Loading -> {
            Routes.SplashScreen.route
        }

        is UiState.Success -> {
            Routes.TeamsScreen.route
        }

        else -> {
            Routes.AuthNav.route
        }
    }
}
