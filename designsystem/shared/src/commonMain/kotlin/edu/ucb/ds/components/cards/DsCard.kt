package edu.ucb.ds.components.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import edu.ucb.ds.theme.DsTheme

// Tarjeta del Design System
@Composable
fun DsCard(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(DsTheme.radius.large),
        colors = CardDefaults.cardColors(
            containerColor = DsTheme.colors.surface,
            contentColor = DsTheme.colors.onSurface
        ),
        border = BorderStroke(1.dp, DsTheme.colors.outline)
    ) {
        Column(
            modifier = Modifier.padding(DsTheme.spacing.large),
            content = content
        )
    }
}