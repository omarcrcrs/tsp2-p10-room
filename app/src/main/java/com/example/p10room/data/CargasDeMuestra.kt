package com.example.p10room.data

import com.example.p10room.model.CargaGasolina
import com.example.p10room.model.TiposCombustible

/**
 * Registros de ejemplo SOLO para los @Preview de Android Studio.
 * La app real siempre lee y escribe en la base de datos de Room.
 */
val cargasDeMuestra = listOf(
    CargaGasolina(
        id = 3,
        tipoCombustible = TiposCombustible.PREMIUM,
        precioPorLitro = 25.89,
        litros = 32.0,
        conPropina = true,
        propina = 10.0,
        kilometraje = 48210.0,
        fecha = 1_793_120_000_000L
    ),
    CargaGasolina(
        id = 2,
        tipoCombustible = TiposCombustible.MAGNA,
        precioPorLitro = 23.68,
        litros = 40.0,
        fecha = 1_792_500_000_000L
    ),
    CargaGasolina(
        id = 1,
        tipoCombustible = TiposCombustible.DIESEL,
        precioPorLitro = 25.49,
        litros = 45.5,
        conPropina = true,
        propina = 15.0,
        kilometraje = 47890.0,
        fecha = 1_791_900_000_000L
    )
)
