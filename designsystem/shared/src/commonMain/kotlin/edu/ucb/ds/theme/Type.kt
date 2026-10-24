package edu.ucb.ds.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class DsTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val label: TextStyle
)

val DefaultDsTypography = DsTypography(
    heading = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
    body = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Normal),
    label = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium)
)