package com.example.p10room.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.p10room.model.CargaGasolina
import kotlinx.coroutines.flow.Flow

/**
 * DAO (Data Access Object): aquí viven las operaciones CRUD sobre la tabla.
 * Solo se declaran; Room (con KSP) genera la implementación al compilar.
 * Las funciones "suspend" se ejecutan en una corrutina, nunca en el hilo principal.
 */
@Dao
interface CargaGasolinaDao {

    // C — Create: inserta una carga nueva
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertar(carga: CargaGasolina)

    // R — Read: todas las cargas, de la más reciente a la más antigua.
    // Devuelve un Flow: cada vez que la tabla cambia, Room emite la lista actualizada.
    @Query("SELECT * FROM cargas_gasolina ORDER BY fecha DESC")
    fun obtenerTodas(): Flow<List<CargaGasolina>>

    // R — Read: una sola carga por su id (para editarla o eliminarla)
    @Query("SELECT * FROM cargas_gasolina WHERE id = :id")
    suspend fun obtenerPorId(id: Int): CargaGasolina?

    // U — Update: Room busca la fila con el mismo id (llave primaria) y la reemplaza
    @Update
    suspend fun actualizar(carga: CargaGasolina)

    // D — Delete: Room borra la fila cuyo id coincide
    @Delete
    suspend fun eliminar(carga: CargaGasolina)
}
