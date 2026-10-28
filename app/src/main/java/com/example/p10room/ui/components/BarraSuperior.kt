@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.p10room.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.p10room.R

/**
 * Barra superior reutilizable para las 4 pantallas.
 * Si recibe [onRegresar], muestra la flecha para volver al historial.
 */
@Composable
fun BarraSuperior(
    titulo: String,
    onRegresar: (() -> Unit)? = null,
    colorContenedor: Color = MaterialTheme.colorScheme.primaryContainer,
    colorContenido: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    TopAppBar(
        title = { Text(text = titulo, fontWeight = FontWeight.Bold) },
        navigationIcon = {
            if (onRegresar != null) {
                IconButton(onClick = onRegresar) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_regresar),
                        contentDescription = stringResource(id = R.string.regresar)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorContenedor,
            titleContentColor = colorContenido,
            navigationIconContentColor = colorContenido
        )
    )
}
