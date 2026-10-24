package edu.ucb.ds.components.textfields

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import edu.ucb.ds.theme.DsTheme

// Campo de texto del Design System
@Composable
fun DsTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    singleLine: Boolean = true,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        enabled = enabled,
        isError = isError,
        singleLine = singleLine,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        keyboardOptions = keyboardOptions,
        visualTransformation = visualTransformation,
        label = { Text(text = label, style = DsTheme.typography.body) },
        textStyle = DsTheme.typography.body,
        shape = RoundedCornerShape(DsTheme.radius.medium),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = DsTheme.colors.onSurface,
            unfocusedTextColor = DsTheme.colors.onSurface,
            focusedBorderColor = DsTheme.colors.primary,
            unfocusedBorderColor = DsTheme.colors.outline,
            focusedLabelColor = DsTheme.colors.primary,
            unfocusedLabelColor = DsTheme.colors.outline,
            cursorColor = DsTheme.colors.primary,
            errorBorderColor = DsTheme.colors.error,
            errorLabelColor = DsTheme.colors.error
        )
    )
}