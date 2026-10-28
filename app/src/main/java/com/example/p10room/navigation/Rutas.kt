package com.example.p10room.navigation

/** Rutas de navegación en un solo lugar (evita errores de dedo al escribirlas). */
object Rutas {
    const val HOME = "Home"
    const val AGREGAR = "AddView"
    const val EDITAR = "EditView/{id}"
    const val ELIMINAR = "DeleteView/{id}"

    /** Nombre del argumento que viaja en la ruta: el id de la carga en la BD. */
    const val ARG_ID = "id"

    fun editar(id: Int) = "EditView/$id"
    fun eliminar(id: Int) = "DeleteView/$id"
}
