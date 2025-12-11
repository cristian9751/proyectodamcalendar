package com.cristian.calendarapp.presentation
sealed class Routes(val route : String) {
    object LoginScreen : Routes("Login")
    object SignUpScreen : Routes("Signup")
    object CalendarScreen : Routes("Calendar")
    object ProfileScreen : Routes("Profile")

    object MainNav : Routes("Main")


    object TeamsScreen : Routes("Teams")

    object NewTeamScreen : Routes("NewTeam")


    object HomeScreen : Routes("Home")

    object AuthNav : Routes("Auth")

    object SplashScreen : Routes("splash")
}