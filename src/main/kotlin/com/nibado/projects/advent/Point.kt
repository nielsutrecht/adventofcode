package com.nibado.projects.advent

import com.nibado.projects.advent.Direction.*

data class Point(val x: Int, val y: Int) : Comparable<Point> {
    fun plus(x: Int, y: Int) = Point(this.x + x, this.y + y)

    operator fun plus(other: Pair<Int, Int>) = Point(x + other.first, y + other.second)
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun plus(direction: Direction) = when (direction) {
        NORTH -> Point(x, y - 1)
        EAST -> Point(x + 1, y)
        SOUTH -> Point(x, y + 1)
        WEST -> Point(x - 1, y)
    }

    override fun compareTo(other: Point) = if (y == other.y) x.compareTo(other.x) else y.compareTo(other.y)

    fun manhattan(other: Point) = Math.abs(x - other.x) + Math.abs(y - other.y)
    fun manhattan() = manhattan(Point(0, 0))

    fun inBound(maxX: Int, maxY: Int) = inBound(0, maxX, 0, maxY)

    fun inBound(minX: Int, maxX: Int, minY: Int, maxY: Int) =
            x in minX..maxX && y in minY..maxY

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

        fun parse(v: String) = parse(v, DEFAULT_PARSE_REGEX)
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