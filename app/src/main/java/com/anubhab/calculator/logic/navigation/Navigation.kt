package com.anubhab.calculator.logic.navigation

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anubhab.calculator.history.HistoryViewModel
import com.anubhab.calculator.logic.CalcViewModel
import com.anubhab.calculator.screens.CalculatorScreen
import com.anubhab.calculator.screens.HistoryScreen
import com.anubhab.calculator.screens.settings.SettingsScreen

@Composable
fun Nav(historyViewModel: HistoryViewModel) {


    val activity = LocalActivity.current
    var screenToDisplay by rememberSaveable { mutableStateOf(Screens.MAIN) }
    val viewModel = viewModel<CalcViewModel>()
    val historyState by historyViewModel.state.collectAsStateWithLifecycle()


    // Mimic back behavior from navigation
    BackHandler {
        if (screenToDisplay != Screens.MAIN) {
            screenToDisplay = Screens.MAIN
        } else {
            activity?.moveTaskToBack(true)
        }
    }

    AnimatedContent(
        targetState = screenToDisplay,
        label = "",
        transitionSpec = { fadeIn() togetherWith fadeOut() }
    ) { screen ->
        when (screen) {
            Screens.MAIN -> {
                CalculatorScreen(
                    viewModel = viewModel,
                    onNavigate = { screenToDisplay = it },
                    historyViewModel = historyViewModel,
                    historyState = historyState
                )
            }

            Screens.HISTORY -> {
                HistoryScreen(
                    state = historyState,
                    onNavigate = { screenToDisplay = it },
                    onEvents = historyViewModel::onEvent
                )
            }

            Screens.SETTINGS -> {
                SettingsScreen(
                    onNavigate = { screenToDisplay = it }
                )
            }
        }
    }

}