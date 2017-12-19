package com.nibado.projects.advent

import com.nibado.projects.advent.Direction.*

data class Point(val x: Int, val y: Int) {
    companion object {
        val NEIGHBORS = (-1 .. 1)
                .flatMap { x -> (-1 .. 1).map { y -> Point(x, y) }}
                .filterNot { it.x == 0 && it.y == 0 }

        val NEIGHBORS_H = listOf(Point(-1, 0), Point(1, 0))
        val NEIGHBORS_V = listOf(Point(0, -1), Point(0, 1))
        val NEIGHBORS_HV = NEIGHBORS_H + NEIGHBORS_V
    }
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
}