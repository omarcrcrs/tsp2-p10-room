package com.example.p10room.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p10room.R
import com.example.p10room.domain.calcularMontoTotal
import com.example.p10room.model.CargaGasolina
import com.example.p10room.model.TiposCombustible
import com.example.p10room.ui.theme.P10roomTheme

/**
 * Formulario de una carga. Lo comparten AddView (cargaInicial = null → registro nuevo)
 * y EditView (cargaInicial = registro leído de Room → se modifica con copy()).
 * Así, agregar un campo a la entidad solo obliga a tocar este archivo.
 */
@Composable
fun FormularioCarga(
    textoBoton: String,
    onGuardar: (CargaGasolina) -> Unit,
    modifier: Modifier = Modifier,
    cargaInicial: CargaGasolina? = null
) {
    // Estado del formulario: sobrevive a rotaciones (rememberSaveable)
    var tipo by rememberSaveable { mutableStateOf(cargaInicial?.tipoCombustible ?: TiposCombustible.MAGNA) }
    var precioTexto by rememberSaveable { mutableStateOf(cargaInicial?.precioPorLitro?.aTextoEditable() ?: "") }
    var litrosTexto by rememberSaveable { mutableStateOf(cargaInicial?.litros?.aTextoEditable() ?: "") }
    var conPropina by rememberSaveable { mutableStateOf(cargaInicial?.conPropina ?: false) }
    var propinaTexto by rememberSaveable {
        mutableStateOf(cargaInicial?.propina?.takeIf { it > 0.0 }?.aTextoEditable() ?: "")
    }
    var guardarKilometraje by rememberSaveable { mutableStateOf((cargaInicial?.kilometraje ?: 0.0) > 0.0) }
    var kilometrajeTexto by rememberSaveable {
        mutableStateOf(cargaInicial?.kilometraje?.takeIf { it > 0.0 }?.aTextoEditable() ?: "")
    }
    // Evita guardar dos veces si se toca rápido el botón
    var enviado by remember { mutableStateOf(false) }

    // Valores derivados: se recalculan solos cada vez que cambia algún estado
    val precio = precioTexto.toDoubleOrNull() ?: 0.0
    val litros = litrosTexto.toDoubleOrNull() ?: 0.0
    val propina = propinaTexto.toDoubleOrNull() ?: 0.0
    val kilometraje = kilometrajeTexto.toDoubleOrNull() ?: 0.0
    val montoTotal = calcularMontoTotal(precio, litros, propina, conPropina)
    val formularioValido = precio > 0.0 && litros > 0.0

    Column(
        modifier = modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = stringResource(R.string.tipo_combustible),
                    style = MaterialTheme.typography.titleSmall
                )
                SelectorCombustible(seleccionado = tipo, alSeleccionar = { tipo = it })

                CampoNumerico(
                    valor = precioTexto,
                    alCambiar = { precioTexto = it },
                    etiqueta = stringResource(R.string.precio_litro),
                    icono = R.drawable.ic_precio
                )
                CampoNumerico(
                    valor = litrosTexto,
                    alCambiar = { litrosTexto = it },
                    etiqueta = stringResource(R.string.litros),
                    icono = R.drawable.ic_litros,
                    accionTeclado = if (conPropina || guardarKilometraje) ImeAction.Next else ImeAction.Done
                )

                OpcionConSwitch(
                    texto = stringResource(R.string.pregunta_propina),
                    activado = conPropina,
                    alCambiar = { conPropina = it }
                )
                AnimatedVisibility(visible = conPropina) {
                    CampoNumerico(
                        valor = propinaTexto,
                        alCambiar = { propinaTexto = it },
                        etiqueta = stringResource(R.string.propina),
                        icono = R.drawable.ic_propina,
                        accionTeclado = if (guardarKilometraje) ImeAction.Next else ImeAction.Done
                    )
                }

                OpcionConSwitch(
                    texto = stringResource(R.string.pregunta_kilometraje),
                    activado = guardarKilometraje,
                    alCambiar = { guardarKilometraje = it }
                )
                AnimatedVisibility(visible = guardarKilometraje) {
                    CampoNumerico(
                        valor = kilometrajeTexto,
                        alCambiar = { kilometrajeTexto = it },
                        etiqueta = stringResource(R.string.kilometraje),
                        icono = R.drawable.ic_kilometraje,
                        accionTeclado = ImeAction.Done
                    )
                }
            }
        }

        TarjetaTotal(monto = montoTotal)

        Button(
            onClick = {
                if (!enviado) {
                    enviado = true
                    // Nuevo: CargaGasolina() trae id = 0 y la fecha de este momento.
                    // Edición: se copia el registro original (conserva id y fecha).
                    val base = cargaInicial ?: CargaGasolina()
                    onGuardar(
                        base.copy(
                            tipoCombustible = tipo,
                            precioPorLitro = precio,
                            litros = litros,
                            conPropina = conPropina,
                            propina = if (conPropina) propina else 0.0,
                            kilometraje = if (guardarKilometraje) kilometraje else 0.0
                        )
                    )
                }
            },
            enabled = formularioValido && !enviado,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            Text(text = textoBoton)
        }

        if (!formularioValido) {
            Text(
                text = stringResource(R.string.formulario_incompleto),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/** 40.0 → "40"  ·  23.68 → "23.68" (para precargar los campos al editar). */
private fun Double.aTextoEditable(): String =
    if (this % 1.0 == 0.0) this.toLong().toString() else this.toString()

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FormularioCargaPreview() {
    P10roomTheme {
        FormularioCarga(textoBoton = "Guardar carga", onGuardar = {})
    }
}
