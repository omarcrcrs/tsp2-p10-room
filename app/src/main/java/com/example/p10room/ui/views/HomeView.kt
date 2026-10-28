package com.example.p10room.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.p10room.R
import com.example.p10room.data.cargasDeMuestra
import com.example.p10room.model.CargaGasolina
import com.example.p10room.ui.components.BarraSuperior
import com.example.p10room.ui.components.CargaCard
import com.example.p10room.ui.components.EstadoVacio
import com.example.p10room.ui.components.IndicadorCargando
import com.example.p10room.ui.components.ResumenCargas
import com.example.p10room.ui.theme.P10roomTheme
import com.example.p10room.viewmodel.CargasViewModel

/** Pantalla principal — operación "Mostrar registros" (Read). */
@Composable
fun HomeView(
    viewModel: CargasViewModel,
    snackbarHostState: SnackbarHostState,
    onAgregar: () -> Unit,
    onEditar: (Int) -> Unit,
    onEliminar: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    // Escucha la tabla: cada insert, update o delete llega aquí como una lista nueva
    val cargas by viewModel.cargas.collectAsStateWithLifecycle()

    HomeContent(
        cargas = cargas,
        snackbarHostState = snackbarHostState,
        onAgregar = onAgregar,
        onEditar = onEditar,
        onEliminar = onEliminar,
        modifier = modifier
    )
}

/** Contenido sin ViewModel (estado y eventos como parámetros) para poder previsualizarlo. */
@Composable
fun HomeContent(
    cargas: List<CargaGasolina>?,
    snackbarHostState: SnackbarHostState,
    onAgregar: () -> Unit,
    onEditar: (Int) -> Unit,
    onEliminar: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = { BarraSuperior(titulo = stringResource(R.string.titulo_historial)) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAgregar,
                icon = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_agregar),
                        contentDescription = null
                    )
                },
                text = { Text(text = stringResource(R.string.agregar_carga)) }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        when {
            cargas == null -> IndicadorCargando(modifier = Modifier.padding(innerPadding))
            cargas.isEmpty() -> EstadoVacio(modifier = Modifier.padding(innerPadding))
            else -> LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                // Espacio extra abajo para que el botón flotante no tape la última tarjeta
                contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item(key = "resumen") {
                    ResumenCargas(cargas = cargas)
                }
                items(items = cargas, key = { carga -> carga.id }) { carga ->
                    CargaCard(
                        carga = carga,
                        onEditar = { onEditar(carga.id) },
                        onEliminar = { onEliminar(carga.id) },
                        modifier = Modifier.animateItem()
                    )
                }
            }
        }
    }
}

// ───────────────────────── Previews ─────────────────────────

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeContentPreview() {
    P10roomTheme {
        HomeContent(
            cargas = cargasDeMuestra,
            snackbarHostState = remember { SnackbarHostState() },
            onAgregar = {},
            onEditar = {},
            onEliminar = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true, name = "Sin registros")
@Composable
fun HomeContentVaciaPreview() {
    P10roomTheme {
        HomeContent(
            cargas = emptyList(),
            snackbarHostState = remember { SnackbarHostState() },
            onAgregar = {},
            onEditar = {},
            onEliminar = {}
        )
    }
}
