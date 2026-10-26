package edu.ucb.ds.components.labels

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import edu.ucb.ds.theme.DsTheme

// Texto basico del Design System
@Composable
fun DsLabel(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = DsTheme.colors.onBackground
) {
    Text(
        text = text,
        modifier = modifier,
        style = DsTheme.typography.body,
        color = color
    )
}