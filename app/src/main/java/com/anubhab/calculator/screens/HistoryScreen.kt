package com.anubhab.calculator.screens

import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anubhab.calculator.R
import com.anubhab.calculator.components.CuteNavigationButton
import com.anubhab.calculator.components.CuteText
import com.anubhab.calculator.components.HistoryActionButtons
import com.anubhab.calculator.history.Calculation
import com.anubhab.calculator.history.HistoryEvents
import com.anubhab.calculator.history.HistoryState
import com.anubhab.calculator.logic.formatOrNot
import com.anubhab.calculator.logic.navigation.Screens
import com.anubhab.calculator.logic.rememberDecimal
import com.anubhab.calculator.logic.rememberSortHistoryASC
import com.anubhab.calculator.logic.rememberUseHistory

@Composable
fun HistoryScreen(
    state: HistoryState,
    onEvents: (HistoryEvents) -> Unit,
    onNavigate: (Screens) -> Unit
) {
    var isHistoryEnable by rememberUseHistory()
    val sortASC by rememberSortHistoryASC()
    val sortedCalculations by remember(state.calculation) {
        derivedStateOf {
            if (sortASC) {
                state.calculation.sortedBy { it.id }
            } else {
                state.calculation.sortedByDescending { it.id }
            }
        }
    }

    Scaffold { paddingValues ->
        Box(Modifier.fillMaxSize()) {
            if (!isHistoryEnable) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CuteText(stringResource(R.string.history_not_enabled))
                    Spacer(Modifier.height(10.dp))
                    Button(
                        onClick = { isHistoryEnable = !isHistoryEnable }
                    ) {
                        CuteText(stringResource(R.string.enable_history))
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = paddingValues
                ) {
                    itemsIndexed(
                        items = sortedCalculations,
                        key = { _, item -> item.id }
                    ) { index, item ->
                        CalculationItem(
                            calculation = item,
                            onEvents = onEvents,
                            topDp = if (index == 0) 24.dp else 4.dp,
                            bottomDp = if (index == state.calculation.lastIndex) 24.dp else 4.dp,
                            modifier = Modifier.animateItem()
                        )
                    }
                }
            }

            CuteNavigationButton(
                modifier = Modifier
                    .padding(start = 15.dp)
                    .align(Alignment.BottomStart)
                    .navigationBarsPadding(),
            ) { onNavigate(it) }
            HistoryActionButtons { onEvents(HistoryEvents.DeleteAllCalculation(state.calculation)) }

        }
    }

}

@Composable
private fun CalculationItem(
    calculation: Calculation,
    onEvents: (HistoryEvents) -> Unit,
    topDp: Dp,
    bottomDp: Dp,
    modifier: Modifier = Modifier
) {
    val decimalSetting by rememberDecimal()

    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 3.dp)
            .clip(
                RoundedCornerShape(
                    topStart = topDp,
                    topEnd = topDp,
                    bottomEnd = bottomDp,
                    bottomStart = bottomDp
                )
            ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                CuteText(
                    text = formatOrNot(calculation.operation, decimalSetting),
                    fontSize = 20.sp,
                    modifier = Modifier.basicMarquee()
                )
                CuteText(
                    text = "= ${formatOrNot(calculation.result, decimalSetting)}",
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                    modifier = Modifier.basicMarquee()
                )
            }
            IconButton(
                onClick = { onEvents(HistoryEvents.DeleteCalculation(calculation)) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.trash_rounded),
                    contentDescription = stringResource(R.string.delete),
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}