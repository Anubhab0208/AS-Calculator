package com.anubhab.calculator.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anubhab.calculator.R
import com.anubhab.calculator.logic.rememberSortHistoryASC

@Composable
fun BoxScope.HistoryActionButtons(
    onDeleteHistory: () -> Unit
) {
    var dropDownExpanded by remember { mutableStateOf(false) }
    var sortASC by rememberSortHistoryASC()

    SmallFloatingActionButton(
        onClick = {},
        modifier = Modifier
            .padding(end = 15.dp)
            .align(Alignment.BottomEnd)
            .navigationBarsPadding(),
        shape = RoundedCornerShape(14.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Row {
            IconButton(
                onClick = { dropDownExpanded = true }
            ) {
                AnimatedContent(
                    targetState = !dropDownExpanded
                ) {
                    Icon(
                        painter = if (it) painterResource(R.drawable.sort_rounded) else painterResource(
                            R.drawable.close
                        ),
                        contentDescription = stringResource(R.string.sort)
                    )
                }
            }
            IconButton(
                onClick = onDeleteHistory
            ) {
                Icon(
                    painter = painterResource(R.drawable.trash_rounded),
                    contentDescription = stringResource(R.string.delete)
                )
            }

            DropdownMenu(
                expanded = dropDownExpanded,
                onDismissRequest = { dropDownExpanded = false },
                shape = RoundedCornerShape(24.dp)
            ) {

                DropdownMenuItem(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (!sortASC) MaterialTheme.colorScheme.surfaceContainer else Color.Transparent),
                    onClick = { sortASC = true },
                    text = { CuteText(stringResource(R.string.ascending)) },
                    leadingIcon = {
                        RadioButton(
                            selected = sortASC,
                            onClick = null
                        )
                    }
                )

                DropdownMenuItem(
                    modifier = Modifier
                        .padding(horizontal = 2.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (!sortASC) MaterialTheme.colorScheme.surfaceContainer else Color.Transparent),
                    onClick = { sortASC = false },
                    text = { CuteText(stringResource(R.string.descending)) },
                    leadingIcon = {
                        RadioButton(
                            selected = !sortASC,
                            onClick = null
                        )
                    }
                )
            }
        }
    }
}