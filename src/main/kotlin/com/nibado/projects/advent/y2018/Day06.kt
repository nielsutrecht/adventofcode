package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.Rectangle
import com.nibado.projects.advent.resourceLines

object Day06 : Day {
    private val input = resourceLines(2018, 6).map { it.split(", ").map { it.toInt() } }.map { Point(it[0], it[1]) }
    private val rect = Rectangle(Point(input.minBy { it.x }!!.x - 1, input.minBy { it.y }!!.y - 1), Point(input.maxBy { it.x }!!.x + 1, input.maxBy { it.y }!!.y + 1))

    override fun part1() = rect.points()
            .map { p -> p to nearest(p) }
            .filterNot { rect.onEdge(it.first) || it.second == null }
            .groupBy { it.second }
            .map { it.key!! to it.value.size }
            .maxBy { it.second }!!.second

    override fun part2() = rect.points().count { distanceSum(it) < 10000 }

    private fun distanceSum(p: Point): Int {
        return input.map { i -> i to i.manhattan(p) }.sumBy { it.second }
    }

    private fun nearest(p: Point): Point? {
        val (a, b) = input.map { i -> i to i.manhattan(p) }
                .sortedBy { it.second }
                .take(2)
        return if (a.second == b.second) {
            null
        } else {
            a.first
        }
    }
}