package com.App.healtcare.ui.quiz.handler

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.App.healtcare.sevice.AppLockService
import com.App.healtcare.ui.quiz.math.QuizViewModel
import com.App.healtcare.ui.quiz.vocabulary.QuizVocabularyScreen
import com.App.healtcare.ui.quiz.vocabulary.domain.VocabularyViewModel
import com.App.healtcare.ui.theme.HealtCareTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizLockActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val lockedPackageName = intent.getStringExtra("LOCKED_PACKAGE")
        setContent {
            HealtCareTheme {
                val viewModel: QuizViewModel = hiltViewModel()
                val vocabViewModel : VocabularyViewModel = hiltViewModel()
                val state by viewModel.uiState.collectAsState()
                val vocabState by vocabViewModel.uiState.collectAsStateWithLifecycle()


                BackHandler {
                    val startMain = Intent(Intent.ACTION_MAIN)
                    startMain.addCategory(Intent.CATEGORY_HOME)
                    startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(startMain)
                    finish()
                }

                QuizHandler(
                    onDismiss = {
                        if (lockedPackageName != null) {
                            AppLockService.Companion.instance?.unlockPackage(lockedPackageName)
                        }
                        finish()
                    }
                )


//                QuizScreen(
//                    state = state,
//                    onSubmitAnswer = { answer ->
//                        viewModel.submitAnswer(answer)
//                    },
//                    onDismiss = {
//                        if (lockedPackageName != null) {
//                            AppLockService.Companion.instance?.unlockPackage(lockedPackageName)
//                        }
//                        finish()
//                    }
//                )
//                QuizVocabularyScreen(
//                    state = vocabState,
//                    onSubmitAnswer = {answer ->
//                        vocabViewModel.submitAnswer(answer)
//
//                    },
//                    onDismiss = {
//                        if(lockedPackageName != null){
//                            AppLockService.Companion.instance?.unlockPackage(lockedPackageName)
//                        }
//                        finish()
//                    }
//                )

//                Box(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .background(MaterialTheme.colorScheme.background),
//                    contentAlignment = Alignment.Center
//                ) {
//                    Text(
//                        text = "HELLO WORLD \nLocked: $lockedPackageName",
//                        color = Color.Black,
//                        fontSize = 24.sp
//                    )
//                    if (state.isLoading || state.currentQuestion == null) {
//                        CircularProgressIndicator()
//                    } else {
//                        QuizDialog(
//                            state = state,
//                            onSubmitAnswer = { answer ->
//                                viewModel.submitAnswer(answer)
//                            },
//                            onDismiss = {
//                                lockedPackageName?.let {
//                                    AppLockService.instance?.unlockPackage(it)
//                                }
//                                finish()
//                            }
//
//                        )
//                    }
//
//                }

            }
        }
    }
}