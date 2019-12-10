package com.nibado.projects.advent

import kotlin.math.sqrt

data class Line(val a: Point, val b: Point) {
    fun manhattan(): Int = a.manhattan(b)
    fun reversed() = Line(b, a)

    fun distanceTo(p: Point) = distBetweenPointAndLine(
        p.x.toDouble(), p.y.toDouble(), a.x.toDouble(), a.y.toDouble(), b.x.toDouble(), b.y.toDouble())

    fun distanceTo2(p: Point) : Double {
        val A: Double = p.x.toDouble() - a.x.toDouble() // position of point rel one end of line

        val B: Double = p.y.toDouble() - a.y.toDouble()
        val C: Double = b.x.toDouble() - a.x.toDouble() // vector along line

        val D: Double = b.y.toDouble() - a.y.toDouble()
        val E = -D // orthogonal vector

        val dot = A * E + B * C
        val len_sq = E * E + C * C

        return Math.abs(dot) / Math.sqrt(len_sq)
    }

    private fun distBetweenPointAndLine(
        x: Double,
        y: Double,
        x1: Double,
        y1: Double,
        x2: Double,
        y2: Double
    ): Double {
        val AB = distBetween(x, y, x1, y1)
        val BC = distBetween(x1, y1, x2, y2)
        val AC = distBetween(x, y, x2, y2)

        val s = (AB + BC + AC) / 2
        val area = sqrt(s * (s - AB) * (s - BC) * (s - AC))

        return 2 * area / BC
    }

    private fun distBetween(x: Double, y: Double, x1: Double, y1: Double): Double {
        val xx = x1 - x
        val yy = y1 - y
        return sqrt(xx * xx + yy * yy)
    }
}