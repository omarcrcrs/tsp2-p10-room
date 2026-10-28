package com.example.p10room.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p10room.R
import com.example.p10room.data.cargasDeMuestra
import com.example.p10room.domain.formatearFecha
import com.example.p10room.domain.formatearMoneda
import com.example.p10room.domain.formatearNumero
import com.example.p10room.domain.montoTotal
import com.example.p10room.model.CargaGasolina
import com.example.p10room.ui.theme.P10roomTheme

/**
 * Tarjeta de una carga en el historial (operación "Mostrar").
 * Sus botones abren las pantallas de Editar y Eliminar con el id del registro.
 */
@Composable
fun CargaCard(
    carga: CargaGasolina,
    onEditar: () -> Unit,
    onEliminar: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 16.dp, end = 8.dp, bottom = 4.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Encabezado: combustible y total destacado
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                EtiquetaCombustible(tipo = carga.tipoCombustible)
                Text(
                    text = formatearMoneda(carga.montoTotal()),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            // id del registro (llave primaria) + fecha de la carga
            Text(
                text = stringResource(R.string.formato_id_fecha, carga.id, formatearFecha(carga.fecha)),
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            HorizontalDivider(modifier = Modifier.padding(top = 4.dp, end = 8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.formato_precio_litro, formatearMoneda(carga.precioPorLitro)),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = stringResource(R.string.formato_litros, formatearNumero(carga.litros)),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = if (carga.conPropina) {
                        stringResource(R.string.formato_propina, formatearMoneda(carga.propina))
                    } else {
                        stringResource(R.string.sin_propina)
                    },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (carga.kilometraje > 0.0) {
                    Text(
                        text = stringResource(R.string.formato_odometro, formatearNumero(carga.kilometraje)),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            // Acciones: Modificar y Eliminar
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEditar) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_editar),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = stringResource(R.string.editar))
                }
                TextButton(
                    onClick = onEliminar,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_eliminar),
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = stringResource(R.string.eliminar))
                }
            }
        }
    }
}

/** Resumen de toda la tabla: número de cargas, litros y gasto total. */
@Composable
fun ResumenCargas(cargas: List<CargaGasolina>, modifier: Modifier = Modifier) {
    val gastoTotal = cargas.sumOf { it.montoTotal() }
    val litrosTotales = cargas.sumOf { it.litros }

    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DatoResumen(valor = cargas.size.toString(), etiqueta = stringResource(R.string.resumen_cargas))
            DatoResumen(
                valor = stringResource(R.string.formato_litros, formatearNumero(litrosTotales)),
                etiqueta = stringResource(R.string.resumen_litros)
            )
            DatoResumen(valor = formatearMoneda(gastoTotal), etiqueta = stringResource(R.string.resumen_gasto))
        }
    }
}

@Composable
private fun DatoResumen(valor: String, etiqueta: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = valor,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(text = etiqueta, style = MaterialTheme.typography.labelMedium)
    }
}

/** Total en vivo dentro del formulario (verde "Pip-Boy" del Ejercicio 2). */
@Composable
fun TarjetaTotal(monto: Double, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = stringResource(R.string.monto_total), style = MaterialTheme.typography.labelLarge)
            Text(
                text = formatearMoneda(monto),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

/** Todos los campos de UNA carga consultada por su id (se usa al confirmar el borrado). */
@Composable
fun DetalleCarga(carga: CargaGasolina, modifier: Modifier = Modifier) {
    ElevatedCard(modifier = modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.etiqueta_combustible),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                EtiquetaCombustible(tipo = carga.tipoCombustible)
            }
            FilaDato(stringResource(R.string.etiqueta_registro), stringResource(R.string.formato_id, carga.id))
            FilaDato(stringResource(R.string.etiqueta_fecha), formatearFecha(carga.fecha))
            FilaDato(stringResource(R.string.precio_litro), formatearMoneda(carga.precioPorLitro))
            FilaDato(stringResource(R.string.litros), stringResource(R.string.formato_litros, formatearNumero(carga.litros)))
            FilaDato(
                etiqueta = stringResource(R.string.propina),
                valor = if (carga.conPropina) formatearMoneda(carga.propina) else stringResource(R.string.sin_propina)
            )
            FilaDato(
                etiqueta = stringResource(R.string.etiqueta_kilometraje),
                valor = if (carga.kilometraje > 0.0) {
                    stringResource(R.string.formato_km, formatearNumero(carga.kilometraje))
                } else {
                    stringResource(R.string.no_registrado)
                }
            )
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.monto_total), style = MaterialTheme.typography.titleMedium)
                Text(
                    text = formatearMoneda(carga.montoTotal()),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun FilaDato(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = etiqueta,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = valor,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium
        )
    }
}

// ───────────────────────── Previews ─────────────────────────

@Preview(showBackground = true)
@Composable
fun CargaCardPreview() {
    P10roomTheme {
        CargaCard(carga = cargasDeMuestra.first(), onEditar = {}, onEliminar = {})
    }
}

@Preview(showBackground = true)
@Composable
fun DetalleCargaPreview() {
    P10roomTheme {
        DetalleCarga(carga = cargasDeMuestra.first())
    }
}
