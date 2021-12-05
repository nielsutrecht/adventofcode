package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day05 : Day {
    private val values = resourceRegex(2021, 5, "([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)")
            .map { (_, x1, y1, x2, y2) -> Line(x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt()) }

    private fun List<Line>.solve() = fold(mutableMapOf<Point, Int>()) { acc, line ->
        line.points().forEach { acc[it] = acc.getOrDefault(it, 0) + 1 };acc
    }.count { (_, v) -> v > 1 }

    override fun part1() = values.filterNot { it.direction() == null }.solve()
    override fun part2() = values.solve()
}