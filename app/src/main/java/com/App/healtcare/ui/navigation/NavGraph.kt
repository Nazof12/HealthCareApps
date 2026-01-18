package com.App.healtcare.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.App.healtcare.ui.feature.extraSettings.main.presentation.ExtraSettings
import com.App.healtcare.ui.feature.home.HomeScreen
import com.App.healtcare.ui.feature.questionSettings.presentation.app_selection.AppCheckedScreen
import com.App.healtcare.ui.feature.questionSettings.presentation.question_settings.mathSettings.MathSettings
import com.App.healtcare.ui.feature.questionSettings.presentation.question_settings._main.QuestionSettings
import com.App.healtcare.ui.feature.questionSettings.presentation._main.SettingsScreen
import com.App.healtcare.ui.feature.questionSettings.presentation._main.domain.AuthViewModel
import com.App.healtcare.ui.feature.questionSettings.presentation.question_settings.masterSettings.representation.MasterSettings
import com.App.healtcare.ui.feature.questionSettings.presentation.question_settings.vocabularySettings.presentation.VocabularyWord

@Composable
fun AppNavGraph(

){
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
                toCheckeApp = { navController.navigate(Route.APPCHECKED) },
                toQuestion = { navController.navigate(Route.QUESTIONSETTINGS) },
                toExtraSettings = { navController.navigate(Route.EXTRASETTINGS) },
            )
        }
        composable(Route.APPCHECKED){
            AppCheckedScreen()
        }
        composable(Route.MATHQUESTION){
            MathSettings(
                navController = navController
            )
        }
        // questionSettings configuration
        composable(Route.QUESTIONSETTINGS){
            QuestionSettings(
                toMath = { navController.navigate(Route.MATHQUESTION) },
                toVocabullary = {navController.navigate(Route.VOCABULARYSETTINGS)},
                navController = navController,
                toMasterSettings = {navController.navigate(Route.MASTERSETTINGS)},
            )
        }
        composable(Route.MASTERSETTINGS){
            MasterSettings(
                navController = navController
            )
        }
        composable(Route.VOCABULARYSETTINGS){
            VocabularyWord(
                navController = navController
            )
        }
        composable(Route.EXTRASETTINGS){
            ExtraSettings(
                navController = navController
            )
        }
    }
}