package com.anubhab.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.anubhab.calculator.history.HistoryDatabase
import com.anubhab.calculator.history.HistoryViewModel
import com.anubhab.calculator.logic.navigation.Nav
import com.anubhab.calculator.logic.rememberAppTheme
import com.anubhab.calculator.ui.theme.AsCalculatorTheme
import com.anubhab.calculator.utils.CuteTheme

class MainActivity : ComponentActivity() {

    // I don't think its necessary having a di framework just for that
    private val historyDb by lazy {
        Room.databaseBuilder(
            context = application,
            klass = HistoryDatabase::class.java,
            name = "history.db"
        ).build()
    }

    private val historyViewModel by viewModels<HistoryViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HistoryViewModel(historyDb.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val theme by rememberAppTheme()
            val isSystemInDarkTheme = isSystemInDarkTheme()

            AsCalculatorTheme {
                WindowCompat
                    .getInsetsController(window, window.decorView)
                    .apply {

                        val isLight =
                            if (theme == CuteTheme.SYSTEM) !isSystemInDarkTheme else theme == CuteTheme.LIGHT

                        isAppearanceLightStatusBars = isLight
                        isAppearanceLightNavigationBars = isLight
                    }

                Scaffold { _ ->
                    Nav(historyViewModel)
                }
            }
        }
    }
}

