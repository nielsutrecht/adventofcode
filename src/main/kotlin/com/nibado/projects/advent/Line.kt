package com.nibado.projects.advent

import kotlin.math.sqrt

data class Line(val a: Point, val b: Point) {
    fun manhattan(): Int = a.manhattan(b)
    fun reversed() = Line(b, a)

    fun distanceTo(p: Point) = distance(p, this)

    companion object {
        fun distance(p: Point, line: Line) : Double {
            val ab = Point.distance(p, line.a)
            val bc = Point.distance(line.a, line.b)
            val ac = Point.distance(p, line.b)

            val s = (ab + bc + ac) / 2
            val area = sqrt(s * (s - ab) * (s - bc) * (s - ac))

            return 2 * area / bc
        }
    }
}