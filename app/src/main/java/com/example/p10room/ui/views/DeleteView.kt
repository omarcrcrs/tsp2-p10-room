package com.example.p10room.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.p10room.R
import com.example.p10room.data.cargasDeMuestra
import com.example.p10room.model.CargaGasolina
import com.example.p10room.ui.components.BarraSuperior
import com.example.p10room.ui.components.DetalleCarga
import com.example.p10room.ui.components.IndicadorCargando
import com.example.p10room.ui.components.MensajeCentrado
import com.example.p10room.ui.theme.P10roomTheme
import com.example.p10room.viewmodel.CargasViewModel

/** Pantalla "Eliminar registro" (Delete): muestra la carga completa y pide confirmación. */
@Composable
fun DeleteView(
    idCarga: Int,
    viewModel: CargasViewModel,
    onMensaje: (String) -> Unit,
    onRegresar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Consulta por id para mostrar exactamente qué se va a borrar
    var carga by remember { mutableStateOf<CargaGasolina?>(null) }
    var buscando by remember { mutableStateOf(true) }
    LaunchedEffect(idCarga) {
        carga = viewModel.obtenerCargaPorId(idCarga)
        buscando = false
    }

    val mensajeEliminado = stringResource(R.string.mensaje_eliminado)

    Scaffold(
        modifier = modifier,
        topBar = {
            BarraSuperior(
                titulo = stringResource(R.string.titulo_eliminar_carga),
                onRegresar = onRegresar,
                colorContenedor = MaterialTheme.colorScheme.errorContainer,
                colorContenido = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    ) { innerPadding ->
        val cargaEncontrada = carga
        when {
            buscando -> IndicadorCargando(modifier = Modifier.padding(innerPadding))
            cargaEncontrada == null -> MensajeCentrado(
                texto = stringResource(R.string.registro_no_encontrado),
                modifier = Modifier.padding(innerPadding)
            )
            else -> DeleteContent(
                carga = cargaEncontrada,
                onConfirmar = {
                    viewModel.eliminarCarga(cargaEncontrada)   // DELETE en Room
                    onMensaje(mensajeEliminado)
                    onRegresar()
                },
                onCancelar = onRegresar,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

/** Contenido sin ViewModel para poder previsualizarlo. */
@Composable
fun DeleteContent(
    carga: CargaGasolina,
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Evita borrar dos veces si se toca rápido el botón
    var confirmado by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_advertencia),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(56.dp)
        )
        Text(
            text = stringResource(R.string.confirmar_eliminacion),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.aviso_eliminacion),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        DetalleCarga(carga = carga)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onCancelar,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
            ) {
                Text(text = stringResource(R.string.cancelar))
            }
            Button(
                onClick = {
                    if (!confirmado) {
                        confirmado = true
                        onConfirmar()
                    }
                },
                enabled = !confirmado,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error,
                    contentColor = MaterialTheme.colorScheme.onError
                ),
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_eliminar),
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = stringResource(R.string.eliminar), fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DeleteContentPreview() {
    P10roomTheme {
        DeleteContent(carga = cargasDeMuestra.first(), onConfirmar = {}, onCancelar = {})
    }
}
