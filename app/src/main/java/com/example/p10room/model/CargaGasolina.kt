package com.example.p10room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entidad de Room: cada objeto CargaGasolina es una fila de la tabla "cargas_gasolina".
 *
 * Tipos de dato que guarda la tabla:
 *  - Int     → id (llave primaria que Room autogenera)
 *  - String  → tipo de combustible (Magna, Premium o Diésel)
 *  - Double  → precio por litro, litros, propina y kilometraje
 *  - Boolean → si se dio propina (SQLite lo guarda como INTEGER 0/1)
 *  - Long    → fecha y hora de la carga en milisegundos; a diferencia de un texto
 *              "dd/MM/yyyy", un número sí se ordena bien con ORDER BY
 *
 * Todos los campos tienen un valor por defecto para poder crear una carga nueva
 * con CargaGasolina() y después llenarla con copy(...).
 */
@Entity(tableName = "cargas_gasolina")
data class CargaGasolina(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "tipo_combustible")
    val tipoCombustible: String = TiposCombustible.MAGNA,

    @ColumnInfo(name = "precio_por_litro")
    val precioPorLitro: Double = 0.0,

    @ColumnInfo(name = "litros")
    val litros: Double = 0.0,

    @ColumnInfo(name = "con_propina")
    val conPropina: Boolean = false,

    @ColumnInfo(name = "propina")
    val propina: Double = 0.0,

    @ColumnInfo(name = "kilometraje")
    val kilometraje: Double = 0.0,

    @ColumnInfo(name = "fecha")
    val fecha: Long = System.currentTimeMillis()
)

/** Combustibles que se venden en las gasolineras de México; se guardan como texto. */
object TiposCombustible {
    const val MAGNA = "Magna"
    const val PREMIUM = "Premium"
    const val DIESEL = "Diésel"

    val todos = listOf(MAGNA, PREMIUM, DIESEL)
}
