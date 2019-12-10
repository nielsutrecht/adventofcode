package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines
import com.nibado.projects.advent.toPoint
import kotlin.math.PI

object Day10 : Day {
    private val map = resourceLines(2019, 10).map { line -> line.toCharArray().toList() }

    private val points: Set<Point> by lazy {
        map.indices.flatMap { y -> map[y].indices.map { x -> x to y } }
            .filter { (x, y) -> map[y][x] == '#' }
            .map { it.toPoint() }
            .toSet()
    }

    private val laser: Pair<Point, Int> by lazy {
        points.map { it to visible(it, points) }.maxBy { it.second }!!
    }

    private fun visible(p: Point, points: Set<Point>): Int = points
        .filterNot { it == p }
        .map { it.angle(p) }
        .distinct()
        .size

    override fun part1() = laser.second

    override fun part2(): Int {
        val rads = points.filterNot { it == laser.first }
            .groupBy {laser.first.angle(it) + PI }
            .map { e -> e.key to e.value.sortedBy { ast -> laser.first.distance(ast) }.toMutableList() }
            .sortedBy { it.first }
            .reversed()

        var count = 0

        while (rads.any { it.second.isNotEmpty() }) {
            val list = rads[count++ % rads.size].second

            if (list.isNotEmpty()) {
                val p = list.removeAt(0)
                if (count == 200) {
                    return p.x * 100 + p.y
                }
            }
        }

        throw RuntimeException()
    }
}