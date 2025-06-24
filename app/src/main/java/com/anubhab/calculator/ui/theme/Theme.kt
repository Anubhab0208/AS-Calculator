@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.anubhab.calculator.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialExpressiveTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.anubhab.calculator.R
import com.anubhab.calculator.logic.rememberAppTheme
import com.anubhab.calculator.utils.CuteTheme
import com.anubhab.calculator.utils.anyDarkColorScheme
import com.anubhab.calculator.utils.anyLightColorScheme

@Composable
fun AsCalculatorTheme(
    content: @Composable () -> Unit
) {

    val isSystemInDarkTheme = isSystemInDarkTheme()
    val appTheme by rememberAppTheme()


    val colorScheme = when (appTheme) {
        CuteTheme.AMOLED -> anyDarkColorScheme().copy(
            surface = Color.Black,
            inverseSurface = Color.White,
            background = Color.Black,
        )

        CuteTheme.SYSTEM -> if (isSystemInDarkTheme) anyDarkColorScheme() else anyLightColorScheme()
        CuteTheme.DARK -> anyDarkColorScheme()
        CuteTheme.LIGHT -> anyLightColorScheme()
        else -> anyDarkColorScheme()
    }



    MaterialExpressiveTheme(
        colorScheme = colorScheme,
        content = content
    )
}

val GlobalFont = FontFamily(Font(R.font.nunito))