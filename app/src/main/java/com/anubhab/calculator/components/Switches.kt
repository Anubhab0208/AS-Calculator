@file:OptIn(ExperimentalMaterial3ExpressiveApi::class)

package com.anubhab.calculator.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anubhab.calculator.R
import com.anubhab.calculator.logic.rememberAppTheme
import com.anubhab.calculator.logic.rememberDecimal
import com.anubhab.calculator.logic.rememberShowClearButton
import com.anubhab.calculator.logic.rememberUseButtonsAnimation
import com.anubhab.calculator.logic.rememberUseHistory
import com.anubhab.calculator.logic.rememberUseSystemFont
import com.anubhab.calculator.logic.rememberVibration
import com.anubhab.calculator.screens.settings.components.LazyRowWithScrollButton
import com.anubhab.calculator.screens.settings.components.ThemeItem
import com.anubhab.calculator.utils.CuteTheme
import com.anubhab.calculator.utils.anyDarkColorScheme
import com.anubhab.calculator.utils.anyLightColorScheme


@Composable
fun UI() {
    val settings = mapOf(
        R.string.buttons_anim to rememberUseButtonsAnimation(),
        R.string.show_clear_button to rememberShowClearButton(),
        R.string.use_sys_font to rememberUseSystemFont()
    )

    Column {
        CuteText(
            text = "UI",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )

        settings.onEachIndexed { index, (text, setting) ->
            SettingsCards(
                checked = setting.value,
                onCheckedChange = { setting.value = !setting.value },
                topDp = if (index == 0) 24.dp else 4.dp,
                bottomDp = if (index == settings.size - 1) 24.dp else 4.dp,
                text = stringResource(text),
                optionalDescription = {
                    if (text == R.string.show_clear_button) {
                        CuteText(
                            text = stringResource(R.string.clear_button_desc),
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun Misc() {
    val settings = mapOf(
        R.string.use_history to rememberUseHistory(),
        R.string.decimal_formatting to rememberDecimal(),
        R.string.haptic_feedback to rememberVibration(),
    )
    Column {
        CuteText(
            text = stringResource(R.string.misc),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )
        settings.onEachIndexed { index, (text, setting) ->
            SettingsCards(
                checked = setting.value,
                onCheckedChange = { setting.value = !setting.value },
                topDp = if (index == 0) 24.dp else 4.dp,
                bottomDp = if (index == settings.size - 1) 24.dp else 4.dp,
                text = stringResource(text)
            )
        }
    }
}

@Composable
fun ThemeManagement() {
    var theme by rememberAppTheme()
    val themeItems = listOf(
        ThemeItem(
            onClick = { theme = CuteTheme.SYSTEM },
            backgroundColor = if (isSystemInDarkTheme()) anyDarkColorScheme().background else anyLightColorScheme().background,
            text = stringResource(R.string.follow_sys),
            isSelected = theme == CuteTheme.SYSTEM,
            iconAndTint = Pair(
                painterResource(R.drawable.system_theme),
                if (isSystemInDarkTheme()) anyDarkColorScheme().onBackground else anyLightColorScheme().onBackground
            )
        ),
        ThemeItem(
            onClick = { theme = CuteTheme.DARK },
            backgroundColor = anyDarkColorScheme().background,
            text = stringResource(R.string.dark_mode),
            isSelected = theme == CuteTheme.DARK,
            iconAndTint = Pair(
                painterResource(R.drawable.dark_mode),
                anyDarkColorScheme().onBackground
            )
        ),
        ThemeItem(
            onClick = { theme = CuteTheme.LIGHT },
            backgroundColor = anyLightColorScheme().background,
            text = stringResource(R.string.light_mode),
            isSelected = theme == CuteTheme.LIGHT,
            iconAndTint = Pair(
                painterResource(R.drawable.light_mode),
                anyLightColorScheme().onBackground
            )
        ),
        ThemeItem(
            onClick = { theme = CuteTheme.AMOLED },
            backgroundColor = Color.Black,
            text = stringResource(R.string.amoled_mode),
            isSelected = theme == CuteTheme.AMOLED,
            iconAndTint = Pair(painterResource(R.drawable.amoled), Color.White)
        )
    )

    Column {
        CuteText(
            text = stringResource(id = R.string.theme),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(horizontal = 34.dp, vertical = 8.dp)
        )
        Card(
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 2.dp)
        ) {
            LazyRowWithScrollButton(
                items = themeItems
            ) { item ->
                ThemeSelector(
                    onClick = item.onClick,
                    backgroundColor = item.backgroundColor,
                    text = item.text,
                    isThemeSelected = item.isSelected,
                    icon = {
                        Icon(
                            painter = item.iconAndTint.first,
                            contentDescription = null,
                            tint = item.iconAndTint.second,
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun SettingsCards(
    checked: Boolean,
    topDp: Dp,
    bottomDp: Dp,
    text: String,
    onCheckedChange: () -> Unit,
    optionalDescription: (@Composable () -> Unit)? = null
) {
    Card(
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.surfaceContainer),
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 2.dp),
        shape = RoundedCornerShape(
            topStart = topDp,
            topEnd = topDp,
            bottomStart = bottomDp,
            bottomEnd = bottomDp
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(1f)
            ) {
                Column {
                    CuteText(
                        text = text
                    )
                    optionalDescription?.invoke()
                }
            }
            Switch(
                checked = checked,
                onCheckedChange = { onCheckedChange() },
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color.Transparent
                )
            )
        }
    }
}

@Composable
fun ThemeSelector(
    onClick: () -> Unit,
    backgroundColor: Color,
    icon: @Composable () -> Unit,
    text: String,
    isThemeSelected: Boolean
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(10.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .padding(10.dp)
                .size(50.dp)
                .clip(MaterialShapes.Cookie9Sided.toShape())
                .border(
                    width = 2.dp,
                    color = if (isThemeSelected) MaterialTheme.colorScheme.secondary else Color.Transparent,
                    shape = MaterialShapes.Cookie9Sided.toShape()
                )
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }
        Spacer(Modifier.weight(1f))
        CuteText(text)
    }
}







