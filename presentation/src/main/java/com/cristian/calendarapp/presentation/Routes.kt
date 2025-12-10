package com.cristian.calendarapp.presentation
sealed class Routes(val route : String) {
    object LoginScreen : Routes("Login")
    object SignUpScreen : Routes("Signup")
    object CalendarScreen : Routes("Calendar")
    object ProfileScreen : Routes("Profile")

    object ProfileNav : Routes("profileNav")

    object AuthNav : Routes("auth")

    object SplashScreen : Routes("splash")
}