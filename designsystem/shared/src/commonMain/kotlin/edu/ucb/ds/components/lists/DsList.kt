package edu.ucb.ds.components.lists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.ucb.ds.theme.DsTheme

// Lista vertical del Design System: el espaciado entre elementos viene del tema
@Composable
fun <T> DsList(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (T) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(DsTheme.spacing.medium)
    ) {
        items(items) { item -> itemContent(item) }
    }
}