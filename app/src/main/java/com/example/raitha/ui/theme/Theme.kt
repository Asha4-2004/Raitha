package com.example.raitha.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = SageGreen,
    secondary = Gold,
    tertiary = Amber,
    background = EarthBrown,
    surface = EarthBrown,
    primaryContainer = EarthBrownLight,
    secondaryContainer = Gold,
    tertiaryContainer = Amber,
    onPrimary = EarthBrown,
    onSecondary = EarthBrown,
    onTertiary = EarthBrown,
    onBackground = GoldLight,
    onSurface = GoldLight,
    onPrimaryContainer = GoldLight,
    onSecondaryContainer = EarthBrown,
    onTertiaryContainer = EarthBrown,
    surfaceVariant = EarthBrownLight,
    onSurfaceVariant = GoldLight,
    outline = SageGreenLight
)

private val LightColorScheme = lightColorScheme(
    primary = SageGreen,
    secondary = Gold,
    tertiary = Amber,
    background = BackgroundLight,
    surface = CardBackground,
    primaryContainer = SageGreenLight,
    secondaryContainer = GoldLight,
    tertiaryContainer = AmberLight,
    onPrimary = EarthBrown,
    onSecondary = EarthBrown,
    onTertiary = EarthBrown,
    onBackground = EarthBrown,
    onSurface = EarthBrown,
    onPrimaryContainer = EarthBrown,
    onSecondaryContainer = EarthBrown,
    onTertiaryContainer = EarthBrown,
    surfaceVariant = SageGreenLight,
    onSurfaceVariant = EarthBrownLight,
    outline = EarthBrownLight
)

@Composable
fun RaithaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false, // Disabled to stick to brand palette
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
