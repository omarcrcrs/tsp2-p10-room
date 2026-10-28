package com.example.p10room.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.p10room.R
import com.example.p10room.ui.components.BarraSuperior
import com.example.p10room.ui.components.FormularioCarga
import com.example.p10room.viewmodel.CargasViewModel

/** Pantalla "Agregar un nuevo registro" (Create). */
@Composable
fun AddView(
    viewModel: CargasViewModel,
    onMensaje: (String) -> Unit,
    onRegresar: () -> Unit,
    modifier: Modifier = Modifier
) {
    // El texto se lee aquí (contexto @Composable) para poder usarlo dentro del onClick
    val mensajeGuardado = stringResource(R.string.mensaje_guardado)

    Scaffold(
        modifier = modifier,
        topBar = {
            BarraSuperior(
                titulo = stringResource(R.string.titulo_nueva_carga),
                onRegresar = onRegresar
            )
        }
    ) { innerPadding ->
        FormularioCarga(
            textoBoton = stringResource(R.string.guardar_carga),
            onGuardar = { cargaNueva ->
                viewModel.agregarCarga(cargaNueva)   // INSERT en Room
                onMensaje(mensajeGuardado)
                onRegresar()
            },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
