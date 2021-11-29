package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.Rectangle
import com.nibado.projects.advent.resourceLines

object Day06 : Day {
    private val input = resourceLines(2018, 6).map { Point.parse(it) }
    private val rect = Rectangle.containing(input)

    override fun part1() = rect.points()
            .map { p -> p to nearest(p) }
            .filterNot { rect.onEdge(it.first) || it.second == null }
            .groupBy { it.second }
            .map { it.key!! to it.value.size }
            .maxByOrNull { it.second }!!.second

    override fun part2() = rect.points().count { distanceSum(it) < 10000 }

    private fun distanceSum(p: Point) = input.map { it.manhattan(p) }.sum()

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