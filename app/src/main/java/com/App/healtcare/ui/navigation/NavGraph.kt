package com.App.healtcare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.App.healtcare.ui.feature.home.HomeScreen
import com.App.healtcare.ui.feature.settings.SettingsScreen

@Composable
fun AppNavGraph(){
    val navController = rememberNavController()
    NavHost(
      navController = navController,
        startDestination = Route.HOME
    ){
        composable(Route.HOME){
            HomeScreen(
                onNavigateToSettings = {
                    navController.navigate(Route.SETTINGS)
                }
            )
        }
        composable(Route.SETTINGS){
            SettingsScreen()
        }
    }
}