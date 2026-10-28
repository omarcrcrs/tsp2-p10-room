package com.example.p10room.domain

import com.example.p10room.model.CargaGasolina
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// ───────────────────────── Lógica pura (sin Compose ni Room) ─────────────────────────
// Viene del Ejercicio 2 (IU y estado). Se movió a la capa "domain" para reutilizarla
// en el formulario, en las tarjetas del historial y en las pruebas unitarias.

/** Formato de México: pesos ($) y fechas dd/MM/yyyy. Fijar el Locale evita el "¤" en el Preview. */
private val LOCALE_MX: Locale = Locale.forLanguageTag("es-MX")

/** Monto total = precio por litro × litros (+ propina, solo si se activó el Switch). */
fun calcularMontoTotal(
    precioPorLitro: Double,
    litros: Double,
    propina: Double,
    conPropina: Boolean
): Double = precioPorLitro * litros + if (conPropina) propina else 0.0

/** Monto total de una carga ya guardada en la base de datos. */
fun CargaGasolina.montoTotal(): Double =
    calcularMontoTotal(precioPorLitro, litros, propina, conPropina)

/** 894.0 → "$894.00" */
fun formatearMoneda(monto: Double): String =
    NumberFormat.getCurrencyInstance(LOCALE_MX).format(monto)

/** 199235.0 → "199,235"   ·   40.5 → "40.5" */
fun formatearNumero(valor: Double): String =
    NumberFormat.getNumberInstance(LOCALE_MX)
        .apply { maximumFractionDigits = 2 }
        .format(valor)

/** Milisegundos desde 1970 → "27/10/2026 10:51" */
fun formatearFecha(milisegundos: Long): String =
    SimpleDateFormat("dd/MM/yyyy HH:mm", LOCALE_MX).format(Date(milisegundos))
