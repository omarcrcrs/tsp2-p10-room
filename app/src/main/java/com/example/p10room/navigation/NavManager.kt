package com.example.p10room.navigation

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.p10room.room.CargaGasolinaDao
import com.example.p10room.ui.views.AddView
import com.example.p10room.ui.views.DeleteView
import com.example.p10room.ui.views.EditView
import com.example.p10room.ui.views.HomeView
import com.example.p10room.viewmodel.CargasViewModel
import kotlinx.coroutines.launch

/**
 * Administra la navegación entre las 4 pantallas del CRUD:
 *   Home (mostrar) → AddView (agregar) · EditView/{id} (modificar) · DeleteView/{id} (eliminar)
 *
 * Las vistas no conocen al NavController: solo avisan qué quieren hacer
 * (onAgregar, onEditar, onRegresar…) y aquí se decide a dónde ir.
 */
@Composable
fun NavManager(dao: CargaGasolinaDao) {
    val navController = rememberNavController()

    // Un solo ViewModel compartido por las 4 pantallas; la fábrica le entrega el DAO.
    val viewModel: CargasViewModel = viewModel(
        factory = viewModelFactory {
            initializer { CargasViewModel(dao) }
        }
    )

    // Un solo SnackbarHostState para toda la app: Agregar, Editar y Eliminar piden
    // el mensaje y regresan; el historial (HomeView) es quien lo muestra.
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val mostrarMensaje: (String) -> Unit = { texto ->
        scope.launch { snackbarHostState.showSnackbar(texto) }
    }

    // Regresa al historial sin sacar nunca al propio historial de la pila
    // (si se toca dos veces "regresar", no queda una pantalla en blanco).
    val regresarAlHistorial: () -> Unit = {
        navController.popBackStack(Rutas.HOME, false)
    }

    NavHost(navController = navController, startDestination = Rutas.HOME) {

        composable(Rutas.HOME) {
            HomeView(
                viewModel = viewModel,
                snackbarHostState = snackbarHostState,
                onAgregar = {
                    navController.navigate(Rutas.AGREGAR) { launchSingleTop = true }
                },
                onEditar = { id -> navController.navigate(Rutas.editar(id)) },
                onEliminar = { id -> navController.navigate(Rutas.eliminar(id)) }
            )
        }

        composable(Rutas.AGREGAR) {
            AddView(
                viewModel = viewModel,
                onMensaje = mostrarMensaje,
                onRegresar = regresarAlHistorial
            )
        }

        // Ruta con argumento: el {id} de la carga viaja como Int
        composable(
            route = Rutas.EDITAR,
            arguments = listOf(navArgument(Rutas.ARG_ID) { type = NavType.IntType })
        ) { entrada ->
            EditView(
                idCarga = entrada.arguments?.getInt(Rutas.ARG_ID) ?: 0,
                viewModel = viewModel,
                onMensaje = mostrarMensaje,
                onRegresar = regresarAlHistorial
            )
        }

        composable(
            route = Rutas.ELIMINAR,
            arguments = listOf(navArgument(Rutas.ARG_ID) { type = NavType.IntType })
        ) { entrada ->
            DeleteView(
                idCarga = entrada.arguments?.getInt(Rutas.ARG_ID) ?: 0,
                viewModel = viewModel,
                onMensaje = mostrarMensaje,
                onRegresar = regresarAlHistorial
            )
        }
    }
}
