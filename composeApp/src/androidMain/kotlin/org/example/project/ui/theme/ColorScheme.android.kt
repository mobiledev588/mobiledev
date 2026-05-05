package org.example.project.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import android.os.Build

import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext

@Composable
@ReadOnlyComposable
actual fun getApplicationColorScheme(useDarkTheme: Boolean): ColorScheme {
    val context = LocalContext.current
    val colors = when {
        (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) -> {
            if (useDarkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        useDarkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }
    return colors
}