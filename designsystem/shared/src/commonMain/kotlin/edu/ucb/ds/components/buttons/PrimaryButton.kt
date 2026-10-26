package edu.ucb.ds.components.buttons

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import edu.ucb.ds.theme.DsTheme

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(DsTheme.radius.medium),
        colors = ButtonDefaults.buttonColors(
            containerColor = DsTheme.colors.primary,
            contentColor = DsTheme.colors.onPrimary
        ),
        contentPadding = PaddingValues(
            horizontal = DsTheme.spacing.large,
            vertical = DsTheme.spacing.medium
        )
    ) {
        Text(
            text = text,
            style = DsTheme.typography.body,
            color = DsTheme.colors.onPrimary
        )
    }
}