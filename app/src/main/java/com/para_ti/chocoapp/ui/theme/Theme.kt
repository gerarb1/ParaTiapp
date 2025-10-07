package com.para_ti.chocoapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Define your custom colors here
val ChocolateBrown = Color(0xFF491303) // Example: A dark brown color
val GoldAccent = Color(0xFFE28343)     // Example: A gold color
val Cream = Color(0xFFFFFDD0)          // Example: A cream color
val DarkBackground = Color(0xFF121212) // Example: A very dark background for dark theme

// Esquema de colores oscuros (para tu app de chocolate)
private val DarkColorScheme = darkColorScheme(
    primary = ChocolateBrown,
    secondary = GoldAccent,
    tertiary = Cream,
    background = DarkBackground,
    surface = Color(0xFF1E1E1E), // This was already defined, which is good
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
)

// Esquema de colores claros
private val LightColorScheme = lightColorScheme(
    primary = Color(491303),
    secondary = GoldAccent,
    tertiary = Cream,
    background = Color.White, // This was already Color.White
    surface = Color(0xFFFFFBFE), // This was already defined
    onPrimary = Color.White,
    onSecondary = Color.White, // Consider if this should be Color.Black or a dark color if GoldAccent is light
    onTertiary = Color.Black,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
)

@Composable
fun Parati_chocolate_appTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography, // Make sure Typography is also defined or imported
        content = content
    )
}
