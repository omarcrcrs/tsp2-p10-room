@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.p10room.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.p10room.model.TiposCombustible
import com.example.p10room.ui.theme.ColorDiesel
import com.example.p10room.ui.theme.ColorMagna
import com.example.p10room.ui.theme.ColorPremium

/** Verde = Magna, rojo = Premium, gris = Diésel (como en las bombas de gasolina). */
fun colorCombustible(tipo: String): Color = when (tipo) {
    TiposCombustible.PREMIUM -> ColorPremium
    TiposCombustible.DIESEL -> ColorDiesel
    else -> ColorMagna
}

/** Círculo del color del combustible. */
@Composable
fun PuntoCombustible(tipo: String, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(12.dp)
            .clip(CircleShape)
            .background(colorCombustible(tipo))
    )
}

/** Punto de color + nombre del combustible (se usa en las tarjetas). */
@Composable
fun EtiquetaCombustible(tipo: String, modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        PuntoCombustible(tipo = tipo)
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = tipo,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

/** Selector del tipo de combustible con FilterChip (uno a la vez). */
@Composable
fun SelectorCombustible(
    seleccionado: String,
    alSeleccionar: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        TiposCombustible.todos.forEach { tipo ->
            FilterChip(
                selected = tipo == seleccionado,
                onClick = { alSeleccionar(tipo) },
                label = { Text(text = tipo) },
                leadingIcon = { PuntoCombustible(tipo = tipo) }
            )
        }
    }
}
