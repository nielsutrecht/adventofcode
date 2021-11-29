package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines
import kotlin.math.PI

object Day10 : Day {
    private val map = resourceLines(2019, 10).map { line -> line.toCharArray().toList() }

    private val points: List<Point> by lazy {
        map.indices.flatMap { y -> map[y].indices.map { x -> Point(x, y) } }.filter { (x, y) -> map[y][x] == '#' }
    }

    private val laser: Pair<Point, Int> by lazy {
        points.map { it to visible(it) }.maxByOrNull { it.second }!!
    }

    private fun visible(p: Point): Int = points.filterNot { it == p }.map { it.angle(p) }.distinct().size

    override fun part1() = laser.second

    override fun part2(): Int {
        val rads = points.filterNot { it == laser.first }
            .groupBy { laser.first.angle(it) + PI }
            .map { e -> e.key to e.value.sortedBy { ast -> laser.first.distance(ast) }.toMutableList() }
            .sortedByDescending { it.first }

        var p = Point(0, 0)

        for (i in 0..199) {
            val list = rads[i % rads.size].second

            if (list.isNotEmpty()) {
                p = list.removeAt(0)
            }
        }

        return p.x * 100 + p.y
    }
}