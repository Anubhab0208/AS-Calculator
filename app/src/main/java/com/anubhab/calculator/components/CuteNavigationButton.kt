package com.anubhab.calculator.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.anubhab.calculator.R
import com.anubhab.calculator.logic.navigation.Screens

@Composable
fun CuteNavigationButton(
    modifier: Modifier = Modifier,
    onNavigate: (Screens) -> Unit
) {
    SmallFloatingActionButton(
        onClick = { onNavigate(Screens.MAIN) },
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer
    ) {
        Icon(
            painter = painterResource(R.drawable.back_arrow),
            contentDescription = stringResource(R.string.back)
        )
    }
}