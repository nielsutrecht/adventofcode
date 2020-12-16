package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.*
import com.nibado.projects.advent.Point.Companion.ORIGIN

object Day03 : Day {
    private val values = resourceString(2015, 3).map {
        when (it) {
            '>' -> Direction.EAST
            '<' -> Direction.WEST
            '^' -> Direction.NORTH
            else -> Direction.SOUTH
        }
    }

    override fun part1() = values.fold(mutableSetOf(ORIGIN) to ORIGIN) { (set, p), dir ->
        val newPoint = p + dir
        set += newPoint
        set to newPoint
    }.first.size

    override fun part2() = values.windowed(2, 2).fold(P2Acc()) { (map, p1, p2), (dir1, dir2) ->
        val newP1 = p1 + dir1
        val newP2 = p2 + dir2

        map[newP1] = map.getOrDefault(newP1, 0) + 1
        map[newP2] = map.getOrDefault(newP2, 0) + 1

        P2Acc(map, newP1, newP2)
    }.houses.count { it.value > 0 }

    data class P2Acc(val houses: MutableMap<Point, Int> = mutableMapOf(ORIGIN to 2), val p1: Point = ORIGIN, val p2: Point = ORIGIN)
}
