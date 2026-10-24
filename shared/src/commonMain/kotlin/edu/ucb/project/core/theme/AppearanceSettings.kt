package edu.ucb.project.core.theme

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import kotlinproject.shared.generated.resources.*

// Ajustes de apariencia: tema y tamano de texto.
// Cada opcion ocupa una linea completa para que el texto nunca se corte.
@Composable
fun AppearanceSettings(modifier: Modifier = Modifier) {
    val controller = LocalThemeController.current

    Column(modifier = modifier.fillMaxWidth()) {
        SettingsTitle(stringResource(Res.string.appearance_theme))
        Column(modifier = Modifier.selectableGroup()) {
            listOf(
                ThemeChoice.SYSTEM to stringResource(Res.string.theme_system),
                ThemeChoice.LIGHT to stringResource(Res.string.theme_light),
                ThemeChoice.DARK to stringResource(Res.string.theme_dark),
                ThemeChoice.HIGH_CONTRAST to stringResource(Res.string.theme_high_contrast)
            ).forEach { (option, label) ->
                OptionRow(
                    label = label,
                    selected = controller.choice == option,
                    onClick = { controller.onChoiceChange(option) }
                )
            }
        }

        SettingsTitle(stringResource(Res.string.appearance_font_size))
        Column(modifier = Modifier.selectableGroup()) {
            listOf(
                FontSizeChoice.SMALL to stringResource(Res.string.font_small),
                FontSizeChoice.NORMAL to stringResource(Res.string.font_normal),
                FontSizeChoice.LARGE to stringResource(Res.string.font_large),
                FontSizeChoice.EXTRA_LARGE to stringResource(Res.string.font_extra_large)
            ).forEach { (option, label) ->
                OptionRow(
                    label = label,
                    selected = controller.fontSize == option,
                    onClick = { controller.onFontSizeChange(option) }
                )
            }
        }
    }
}

@Composable
private fun SettingsTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)
            .semantics { heading() }
    )
}

@Composable
private fun OptionRow(label: String, selected: Boolean, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 48.dp)
            .selectable(selected = selected, onClick = onClick, role = Role.RadioButton)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = selected, onClick = null)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(start = 12.dp)
        )
    }
}