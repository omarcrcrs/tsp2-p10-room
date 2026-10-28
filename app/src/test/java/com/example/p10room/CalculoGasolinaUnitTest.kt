package com.example.p10room

import com.example.p10room.domain.calcularMontoTotal
import com.example.p10room.domain.montoTotal
import com.example.p10room.model.CargaGasolina
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Pruebas locales de la capa domain (siguen la línea del Ejercicio 5: 22.35 $/L × 40 L).
 * No necesitan emulador porque la lógica no depende de Android ni de Room.
 */
class CalculoGasolinaUnitTest {

    @Test
    fun calcularMontoTotal_sinPropina_22_35_por_40_litros() {
        val total = calcularMontoTotal(
            precioPorLitro = 22.35,
            litros = 40.0,
            propina = 10.0,
            conPropina = false
        )
        assertEquals(894.0, total, 0.001)
    }

    @Test
    fun calcularMontoTotal_conPropina_sumaLaPropina() {
        val total = calcularMontoTotal(
            precioPorLitro = 22.35,
            litros = 40.0,
            propina = 10.0,
            conPropina = true
        )
        assertEquals(904.0, total, 0.001)
    }

    @Test
    fun montoTotal_deUnaCargaGuardada() {
        val carga = CargaGasolina(precioPorLitro = 25.0, litros = 20.0, conPropina = true, propina = 5.0)
        assertEquals(505.0, carga.montoTotal(), 0.001)
    }
}
