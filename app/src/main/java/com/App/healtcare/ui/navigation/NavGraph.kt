package com.App.healtcare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.App.healtcare.ui.feature.home.HomeScreen
import com.App.healtcare.ui.feature.settings.presentation.app_selection.AppCheckedScreen
import com.App.healtcare.ui.feature.settings.presentation.question_settings.addition.MathSettings
import com.App.healtcare.ui.feature.settings.presentation.question_settings._main.QuestionSettings
import com.App.healtcare.ui.feature.settings.presentation._main.SettingsScreen

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
            SettingsScreen(
                toCheckeApp = {navController.navigate(Route.APPCHECKED)},
                toQuestion = {navController.navigate(Route.QUESTIONSETTINGS)}
            )
        }
        composable(Route.APPCHECKED){
            AppCheckedScreen()
        }
        composable(Route.MATHQUESTION){
            MathSettings()
        }
        composable(Route.QUESTIONSETTINGS){
            QuestionSettings(
                toMath = {navController.navigate(Route.MATHQUESTION)},
                toVocabullary = {}
            )
        }
    }
}