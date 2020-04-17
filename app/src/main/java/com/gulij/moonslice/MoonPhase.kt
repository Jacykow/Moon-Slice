package com.gulij.moonslice

import com.gulij.moonslice.enums.PhaseAlgorithm
import java.time.LocalDateTime
import java.util.*
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.sin

object MoonPhase {
    fun calculate(algorithm: PhaseAlgorithm, date: LocalDateTime): Int {
        return calculate(algorithm, date.year, date.monthValue, date.dayOfMonth)
    }

    private fun calculate(algorithm: PhaseAlgorithm, year: Int, month: Int, day: Int): Int {
        return when (algorithm) {
            PhaseAlgorithm.simple -> simple(year, month, day)
            PhaseAlgorithm.conway -> conway(year, month, day)
            PhaseAlgorithm.trig1 -> trig1(year, month, day)
            PhaseAlgorithm.trig2 -> trig2(year, month, day)
        }.toInt().coerceIn(1, 30) - 1
    }

    private fun simple(year: Int, month: Int, day: Int): Double {
        val lp = 2551443
        val now = Date(year, month - 1, day, 20, 35, 0)
        val newMoon = Date(1970, 0, 7, 20, 35, 0)
        val phase = ((now.time - newMoon.time) / 1000) % lp
        return floor((phase / (24 * 3600)).toDouble()) + 1
    }

    private fun conway(year: Int, month: Int, day: Int): Double {
        var r: Double = year % 100.0
        r %= 19
        if (r > 9) {
            r -= 19
        }
        r = ((r * 11) % 30) + month + day
        if (month < 3) {
            r += 2
        }
        if (year < 2000) r -= 4 else r -= 8.3
        r = floor(r + 0.5) % 30
        return if (r < 0) r + 30 else r
    }

    private fun trig1(year: Int, month: Int, day: Int): Double {
        val thisJD = julDay(year, month, day)
        val degToRad = 3.14159265 / 180
        val k0 = floor((year - 1900) * 12.3685)
        val t1 = (year - 1899.5) / 100
        val t2 = t1 * t1
        val t3 = t1 * t1 * t1
        val j0 = 2415020 + 29 * k0
        val f0 =
            0.0001178 * t2 - 0.000000155 * t3 + (0.75933 + 0.53058868 * k0) - (0.000837 * t1 + 0.000335 * t2)
        val m0 =
            360 * (getFraction(k0 * 0.08084821133)) + 359.2242 - 0.0000333 * t2 - 0.00000347 * t3
        val m1 =
            360 * (getFraction(k0 * 0.07171366128)) + 306.0253 + 0.0107306 * t2 + 0.00001236 * t3
        val b1 =
            360 * (getFraction(k0 * 0.08519585128)) + 21.2964 - (0.0016528 * t2) - (0.00000239 * t3)
        var phase = 0
        var jday = 0.0
        var oldJ = 0.0
        while (jday < thisJD) {
            var f = f0 + 1.530588 * phase
            val m5 = (m0 + phase * 29.10535608) * degToRad
            val m6 = (m1 + phase * 385.81691806) * degToRad
            val b6 = (b1 + phase * 390.67050646) * degToRad
            f -= 0.4068 * sin(m6) + (0.1734 - 0.000393 * t1) * sin(m5)
            f += 0.0161 * sin(2 * m6) + 0.0104 * sin(2 * b6)
            f -= 0.0074 * sin(m5 - m6) - 0.0051 * sin(m5 + m6)
            f += 0.0021 * sin(2 * m5) + 0.0010 * sin(2 * b6 - m6)
            f += 0.5 / 1440
            oldJ = jday
            jday = j0 + 28 * phase + floor(f)
            phase++
        }
        return (thisJD - oldJ) % 30
    }

    private fun getFraction(fr: Double): Double {
        return (fr - floor(fr))
    }

    private fun trig2(year: Int, month: Int, day: Int): Double {
        val n = floor(12.37 * (year - 1900 + ((1.0 * month - 0.5) / 12.0)))
        val rad = 3.14159265 / 180.0
        val t = n / 1236.85
        val t2 = t * t
        val aS = 359.2242 + 29.105356 * n
        val am = 306.0253 + 385.816918 * n + 0.010730 * t2
        var xtra = 0.75933 + 1.53058868 * n + ((1.178e-4) - (1.55e-7) * t) * t2
        xtra += (0.1734 - 3.93e-4 * t) * sin(rad * aS) - 0.4068 * sin(rad * am)
        val i: Double?
        i = if (xtra > 0.0) floor(xtra) else ceil(xtra - 1.0)
        val j1 = julDay(year, month, day)
        val jd = (2415020 + 28 * n) + i
        return (j1 - jd + 30) % 30
    }

    private fun julDay(year: Int, month: Int, day: Int): Double {
        var y = year
        if (year < 0) {
            y++
        }
        var jy = y
        var jm = month + 1
        if (month <= 2) {
            jy--
            jm += 12
        }
        var jul = floor(365.25 * jy) + floor(30.6001 * jm) + day + 1720995
        if (day + 31 * (month + 12 * y) >= (15 + 31 * (10 + 12 * 1582))) {
            val ja = floor(0.01 * jy)
            jul = jul + 2 - ja + floor(0.25 * ja)
        }
        return jul
    }
}