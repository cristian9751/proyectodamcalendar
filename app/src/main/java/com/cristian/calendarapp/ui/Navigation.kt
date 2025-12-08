package com.cristian.calendarapp.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cristian.calendarapp.presentation.Routes
import com.cristian.calendarapp.presentation.screens.LoginScreen
import com.cristian.calendarapp.presentation.screens.SignUpScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = Routes.LoginScreen.route) {
        composable(route = Routes.LoginScreen.route) {
            LoginScreen(navController)
        }

        composable(route = Routes.SignUpScreen.route) {
            SignUpScreen(navController)
        }

        composable(route = Routes.ProfileScreen.route) {
            //Profile screen
        }

        composable(route = Routes.CalendarScreen.route) {
            //Calendar screen
        }
    }
}