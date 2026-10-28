package com.example.p10room.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.p10room.model.CargaGasolina
import com.example.p10room.room.CargaGasolinaDao
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * ViewModel de la bitácora de cargas.
 * Las vistas nunca hablan con Room directamente: le piden cada operación a este
 * ViewModel, que la ejecuta en una corrutina (viewModelScope) para no congelar la IU.
 */
class CargasViewModel(private val dao: CargaGasolinaDao) : ViewModel() {

    /**
     * Lista de cargas que observa la pantalla principal.
     *  - null        → Room todavía no responde (se muestra un indicador de carga)
     *  - lista vacía → la tabla no tiene registros
     */
    val cargas: StateFlow<List<CargaGasolina>?> = dao.obtenerTodas()
        .stateIn<List<CargaGasolina>?>(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    /** Create: guarda una carga nueva (id = 0 para que Room lo autogenere). */
    fun agregarCarga(carga: CargaGasolina) {
        viewModelScope.launch {
            dao.insertar(carga.copy(id = 0))
        }
    }

    /** Read: busca una sola carga por su id (null si ya no existe). */
    suspend fun obtenerCargaPorId(id: Int): CargaGasolina? = dao.obtenerPorId(id)

    /** Update: reemplaza los datos de una carga existente (conserva su id y su fecha). */
    fun actualizarCarga(carga: CargaGasolina) {
        viewModelScope.launch {
            dao.actualizar(carga)
        }
    }

    /** Delete: elimina la carga de la base de datos. */
    fun eliminarCarga(carga: CargaGasolina) {
        viewModelScope.launch {
            dao.eliminar(carga)
        }
    }
}
