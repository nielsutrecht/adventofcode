package com.nibado.projects.advent

import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt

data class Line(val a: Point, val b: Point) {

    constructor(x1: Int, y1: Int, x2: Int, y2: Int) : this(Point(x1, y1), Point(x2, y2))

    fun manhattan(): Int = a.manhattan(b)
    fun reversed() = Line(b, a)
    fun angle() = a.angle(b)

    fun points() : Sequence<Point> =
        sequence {
            var current = a

            val vector = vector()
            yield(current)
            while(current != b) {
                current += vector
                yield(current)
            }
        }

    fun vector() = angle().let { angle -> Point(cos(angle).roundToInt(), sin(angle).roundToInt()) }

    fun direction() : Direction? = when {
        b.x == a.x && b.y < a.y -> Direction.NORTH
        b.x == a.x && b.y > a.y -> Direction.SOUTH
        b.y == a.y && b.x < a.x -> Direction.WEST
        b.y == a.y && b.x > a.x -> Direction.EAST
        else -> null
    }

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