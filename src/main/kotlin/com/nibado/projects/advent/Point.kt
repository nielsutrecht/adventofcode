package com.nibado.projects.advent

import com.nibado.projects.advent.Direction.*
import kotlin.math.*


data class Point(val x: Int, val y: Int) : Comparable<Point> {
    fun plus(x: Int, y: Int) = Point(this.x + x, this.y + y)

    operator fun plus(other: Pair<Int, Int>) = Point(x + other.first, y + other.second)
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun plus(direction: Direction) = plus(direction, 1)

    fun plus(direction: Direction, amount: Int) = when (direction) {
        NORTH -> Point(x, y - amount)
        EAST -> Point(x + amount, y)
        SOUTH -> Point(x, y + amount)
        WEST -> Point(x - amount, y)
    }

    operator fun times(amount: Int) : Point = Point(x * amount, y * amount)

    override fun compareTo(other: Point) = if (y == other.y) x.compareTo(other.x) else y.compareTo(other.y)

    fun manhattan(other: Point) = abs(x - other.x) + Math.abs(y - other.y)
    fun manhattan() = manhattan(Point(0, 0))

    fun distance(other: Point): Double = distance(this, other)

    fun angle(target: Point) = atan2((target.x - x).toDouble(), (target.y - y).toDouble())

    fun inBound(maxX: Int, maxY: Int) = inBound(0, maxX, 0, maxY)

    fun inBound(minX: Int, maxX: Int, minY: Int, maxY: Int) =
            x in minX..maxX && y in minY..maxY

    fun inBound(xRange: IntRange, yRange: IntRange) = x in xRange && y in yRange

    fun neighborsHv() = NEIGHBORS_HV.map { Point(it.x + this.x, it.y + this.y) }
    fun neighborsH() = NEIGHBORS_H.map { Point(it.x + this.x, it.y + this.y) }
    fun neighborsV() = NEIGHBORS_V.map { Point(it.x + this.x, it.y + this.y) }
    fun neighbors() = NEIGHBORS.map { Point(it.x + this.x, it.y + this.y) }

    fun directionTo(other: Point) = when {
        other.x == this.x && other.y < this.y -> Direction.NORTH
        other.x == this.x && other.y > this.y -> Direction.SOUTH
        other.y == this.y && other.x < this.x -> Direction.WEST
        other.y == this.y && other.x > this.x -> Direction.EAST
        else -> throw IllegalArgumentException("No NSEW direction between $this and $other")
    }

    fun rotate90() = Point(-y, x)
    fun rotate(degrees: Int) = rotate(degrees * (Math.PI / 180.0))

    fun rotate(angle: Double): Point {
        val s = sin(angle)
        val c = cos(angle)

        val nx = x * c - y * s
        val ny = x * s + y * c

        return Point(nx.roundToInt(), ny.roundToInt())
    }

    fun down(amount: Int = 1) = copy(y = y + amount)
    fun up(amount: Int = 1) = copy(y = y - amount)
    fun left(amount: Int = 1) = copy(x = x - amount)
    fun right(amount: Int = 1) = copy(x = x + amount)

    companion object {
        fun parse(v: String, r: Regex) = tryParse(v, r)
                ?: throw IllegalArgumentException("Can't parse $v with regex ${r.pattern}")

        fun tryParse(v: String, r: Regex) =
                r.matchEntire(v)
                        ?.groupValues
                        ?.drop(1)
                        ?.let { (x, y) -> Point(x.toInt(), y.toInt()) }

        fun distance(a: Point, b: Point): Double =
                sqrt(((b.y - a.y) * (b.y - a.y) + (b.x - a.x) * (b.x - a.x)).toDouble())

        fun parse(v: String) = parse(v, DEFAULT_PARSE_REGEX)

        fun ray(step: Point) = sequence {
            var current = step

            while (true) {
                yield(current)
                current += step
            }
        }

        val NEIGHBORS = (-1..1)
                .flatMap { x -> (-1..1).map { y -> Point(x, y) } }
                .filterNot { it.x == 0 && it.y == 0 }

        val NEIGHBORS_H = listOf(Point(-1, 0), Point(1, 0))
        val NEIGHBORS_V = listOf(Point(0, -1), Point(0, 1))
        val NEIGHBORS_HV = NEIGHBORS_H + NEIGHBORS_V

        private val DEFAULT_PARSE_REGEX = "(-?[0-9]+)[,:; ]+(-?[0-9]+)".toRegex()
    }
}

fun Pair<Int, Int>.toPoint() = Point(this.first, this.second)

fun IntRange.points(yRange: IntRange) = yRange.flatMap { y -> this.map { x -> Point(x, y) } }.asSequence()
fun IntRange.points() = points(this)

fun Collection<Point>.minX() = this.map { it.x }.min()
fun Collection<Point>.minY() = this.map { it.y }.min()
fun Collection<Point>.maxX() = this.map { it.x }.max()
fun Collection<Point>.maxY() = this.map { it.y }.max()

fun Collection<Point>.bounds() =
        this.fold(listOf(Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)) { list, point ->
            listOf(
                    min(
                            list[0],
                            point.x
                    ), min(list[1], point.y), max(list[2], point.x), max(list[3], point.y)
            )
        }
                .let { (minX, minY, maxX, maxY) -> Point(minX, minY) to Point(maxX, maxY) }

fun Pair<Point, Point>.points() = (this.first.y..this.second.y)
        .flatMap { y ->
            (this.first.x..this.second.x)
                    .map { x -> Point(x, y) }
        }
        .asSequence()
