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

// Ajustes de apariencia: tema y tamano de texto.
// Cada opcion ocupa una linea completa para que el texto nunca se corte.
@Composable
fun AppearanceSettings(modifier: Modifier = Modifier) {
    val controller = LocalThemeController.current

    Column(modifier = modifier.fillMaxWidth()) {
        SettingsTitle("Tema")
        Column(modifier = Modifier.selectableGroup()) {
            listOf(
                ThemeChoice.SYSTEM to "Seguir al sistema",
                ThemeChoice.LIGHT to "Claro",
                ThemeChoice.DARK to "Oscuro",
                ThemeChoice.HIGH_CONTRAST to "Alto contraste"
            ).forEach { (option, label) ->
                OptionRow(
                    label = label,
                    selected = controller.choice == option,
                    onClick = { controller.onChoiceChange(option) }
                )
            }
        }

        SettingsTitle("Tama\u00f1o de texto")
        Column(modifier = Modifier.selectableGroup()) {
            listOf(
                FontSizeChoice.SMALL to "Peque\u00f1o",
                FontSizeChoice.NORMAL to "Normal",
                FontSizeChoice.LARGE to "Grande",
                FontSizeChoice.EXTRA_LARGE to "Muy grande"
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