package com.nibado.projects.advent

import com.nibado.projects.advent.Direction.*

data class Point(val x: Int, val y: Int) {
    fun add(other: Point) = Point(x + other.x, y + other.y)
    fun add(direction: Direction) = when(direction) {
        NORTH -> Point(x, y - 1)
        EAST -> Point(x + 1, y)
        SOUTH -> Point(x, y + 1)
        WEST -> Point(x - 1, y)
    }

    fun manhattan(other: Point) = Math.abs(x - other.x) + Math.abs(y - other.y)
    fun manhattan() = manhattan(Point(0, 0))

    fun inBound(maxX: Int, maxY: Int) = inBound(0, maxX, 0, maxY)

    fun inBound(minX: Int, maxX: Int, minY: Int, maxY: Int) =
            x in minX..maxX && y in minY..maxY

    fun neighbors() = (-1 .. 1)
            .flatMap { x -> (-1 .. 1)
            .map { y -> Point(x + this.x, y + this.y) } }
            .filterNot { it == this }
}