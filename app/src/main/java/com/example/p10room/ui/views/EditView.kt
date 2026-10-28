package com.example.p10room.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.p10room.R
import com.example.p10room.model.CargaGasolina
import com.example.p10room.ui.components.BarraSuperior
import com.example.p10room.ui.components.FormularioCarga
import com.example.p10room.ui.components.IndicadorCargando
import com.example.p10room.ui.components.MensajeCentrado
import com.example.p10room.viewmodel.CargasViewModel

/** Pantalla "Modificar registro" (Update). Recibe el id de la carga desde la ruta. */
@Composable
fun EditView(
    idCarga: Int,
    viewModel: CargasViewModel,
    onMensaje: (String) -> Unit,
    onRegresar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Consulta por id (SELECT ... WHERE id = :id) una sola vez al abrir la pantalla
    var carga by remember { mutableStateOf<CargaGasolina?>(null) }
    var buscando by remember { mutableStateOf(true) }
    LaunchedEffect(idCarga) {
        carga = viewModel.obtenerCargaPorId(idCarga)
        buscando = false
    }

    val mensajeActualizado = stringResource(R.string.mensaje_actualizado)

    Scaffold(
        modifier = modifier,
        topBar = {
            BarraSuperior(
                titulo = stringResource(R.string.titulo_editar_carga),
                onRegresar = onRegresar
            )
        }
    ) { innerPadding ->
        // Copia local para que Kotlin pueda hacer smart cast de CargaGasolina? a CargaGasolina
        val cargaEncontrada = carga
        when {
            buscando -> IndicadorCargando(modifier = Modifier.padding(innerPadding))
            cargaEncontrada == null -> MensajeCentrado(
                texto = stringResource(R.string.registro_no_encontrado),
                modifier = Modifier.padding(innerPadding)
            )
            else -> FormularioCarga(
                textoBoton = stringResource(R.string.guardar_cambios),
                cargaInicial = cargaEncontrada,
                onGuardar = { cargaEditada ->
                    viewModel.actualizarCarga(cargaEditada)   // UPDATE en Room
                    onMensaje(mensajeActualizado)
                    onRegresar()
                },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
