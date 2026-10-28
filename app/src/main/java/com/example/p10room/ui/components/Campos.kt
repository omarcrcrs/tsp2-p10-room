package com.example.p10room.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

/** Solo números con punto decimal: hasta 7 enteros y 3 decimales ("22.35", "40", "199235"). */
private val FORMATO_DECIMAL = Regex("""\d{0,7}(\.\d{0,3})?""")

/**
 * Campo numérico reutilizable (viene del Ejercicio 2): ícono propio a la izquierda
 * y teclado decimal. Acepta coma o punto y descarta lo que no sea un número válido.
 */
@Composable
fun CampoNumerico(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    @DrawableRes icono: Int,
    modifier: Modifier = Modifier,
    accionTeclado: ImeAction = ImeAction.Next
) {
    OutlinedTextField(
        value = valor,
        onValueChange = { nuevo ->
            val normalizado = nuevo.replace(',', '.')
            if (FORMATO_DECIMAL.matches(normalizado)) alCambiar(normalizado)
        },
        label = { Text(text = etiqueta) },
        singleLine = true,
        leadingIcon = {
            Icon(
                painter = painterResource(id = icono),
                contentDescription = null,
                tint = Color.Unspecified, // respeta los colores originales del ícono
                modifier = Modifier.size(28.dp)
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Decimal,
            imeAction = accionTeclado
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = modifier.fillMaxWidth()
    )
}

/** Pregunta con un Switch a la derecha (como AddOptionalData del ejemplo del profe). */
@Composable
fun OpcionConSwitch(
    texto: String,
    activado: Boolean,
    alCambiar: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Switch(
            checked = activado,
            onCheckedChange = alCambiar,
            // Verde "Pip-Boy" del Ejercicio 2 (es el color tertiary del tema)
            colors = SwitchDefaults.colors(checkedTrackColor = MaterialTheme.colorScheme.tertiary)
        )
    }
}
