package com.example.p10room.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
    primary = AzulGris40,
    onPrimary = Color.White,
    primaryContainer = AzulGris90,
    onPrimaryContainer = AzulGris10,
    secondary = AzulGris50,
    onSecondary = Color.White,
    secondaryContainer = AzulGris95,
    onSecondaryContainer = AzulGris10,
    tertiary = VerdePipBoy,
    onTertiary = Color.White,
    tertiaryContainer = VerdePipBoy90,
    onTertiaryContainer = VerdePipBoy10,
    background = FondoClaro,
    onBackground = TextoClaro,
    surface = SuperficieClara,
    onSurface = TextoClaro,
    surfaceVariant = VarianteClara,
    onSurfaceVariant = TextoVarianteClaro,
    outline = ContornoClaro,
    error = ErrorClaro,
    errorContainer = ErrorContenedorClaro,
    onErrorContainer = TextoErrorContenedorClaro,
    surfaceContainerLowest = ContenedorClaroMasBajo,
    surfaceContainerLow = ContenedorClaroBajo,
    surfaceContainer = ContenedorClaro,
    surfaceContainerHigh = ContenedorClaroAlto,
    surfaceContainerHighest = ContenedorClaroMasAlto
)

private val DarkColorScheme = darkColorScheme(
    primary = AzulGris80,
    onPrimary = AzulGris20,
    primaryContainer = AzulGris30,
    onPrimaryContainer = AzulGris90,
    secondary = AzulGris70,
    onSecondary = AzulGris20,
    secondaryContainer = AzulGrisSecundario30,
    onSecondaryContainer = AzulGris95,
    tertiary = VerdePipBoy80,
    onTertiary = VerdePipBoy10,
    tertiaryContainer = VerdePipBoy,
    onTertiaryContainer = VerdePipBoy90,
    background = FondoOscuro,
    onBackground = TextoOscuro,
    surface = SuperficieOscura,
    onSurface = TextoOscuro,
    surfaceVariant = VarianteOscura,
    onSurfaceVariant = TextoVarianteOscuro,
    outline = ContornoOscuro,
    error = ErrorOscuro,
    onError = TextoErrorOscuro,
    errorContainer = ErrorContenedorOscuro,
    onErrorContainer = ErrorContenedorClaro,
    surfaceContainerLowest = ContenedorOscuroMasBajo,
    surfaceContainerLow = ContenedorOscuroBajo,
    surfaceContainer = ContenedorOscuro,
    surfaceContainerHigh = ContenedorOscuroAlto,
    surfaceContainerHighest = ContenedorOscuroMasAlto
)

/**
 * Tema de la app. dynamicColor queda apagado por defecto para que en Android 12+
 * se vea la paleta azul-grisácea propia y no los colores del fondo de pantalla.
 */
@Composable
fun P10roomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
