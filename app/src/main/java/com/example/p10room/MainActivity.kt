package com.example.p10room

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.p10room.navigation.NavManager
import com.example.p10room.room.GasolinaDatabase
import com.example.p10room.ui.theme.P10roomTheme

/**
 * Ejercicio 10 — Bases de datos (Room).
 * Continuación de la calculadora de gasolina de los ejercicios 2 (IU y estado)
 * y 5 (Testing): ahora cada cálculo se guarda como una "carga" en una base de
 * datos local y se puede agregar, mostrar, modificar y eliminar (CRUD).
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Una sola instancia de la base de datos para toda la app (singleton).
        // El DAO viaja al NavManager, que se lo entrega al ViewModel.
        val dao = GasolinaDatabase.obtenerBaseDeDatos(applicationContext).cargaGasolinaDao()

        setContent {
            P10roomTheme {
                NavManager(dao = dao)
            }
        }
    }
}
