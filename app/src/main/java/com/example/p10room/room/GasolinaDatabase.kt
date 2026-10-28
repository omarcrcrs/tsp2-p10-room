package com.example.p10room.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.p10room.model.CargaGasolina

/**
 * Base de datos de la app. Room es un ORM: traduce las clases de Kotlin
 * (la entidad CargaGasolina) a tablas de SQLite y crea el archivo "gasolina_db".
 *
 * Si el esquema cambia (agregar o quitar un campo de la entidad) hay que subir
 * "version" y escribir una Migration para no borrar los datos del usuario.
 * Mientras se desarrolla, basta con desinstalar la app del emulador.
 */
@Database(entities = [CargaGasolina::class], version = 1, exportSchema = false)
abstract class GasolinaDatabase : RoomDatabase() {

    abstract fun cargaGasolinaDao(): CargaGasolinaDao

    companion object {
        // Patrón singleton: una sola instancia de la base de datos para toda la app.
        // @Volatile hace que todos los hilos vean siempre el valor más reciente.
        @Volatile
        private var instancia: GasolinaDatabase? = null

        fun obtenerBaseDeDatos(context: Context): GasolinaDatabase =
            instancia ?: synchronized(this) {
                instancia ?: Room.databaseBuilder(
                    context.applicationContext,
                    GasolinaDatabase::class.java,
                    "gasolina_db"
                ).build().also { instancia = it }
            }
    }
}
