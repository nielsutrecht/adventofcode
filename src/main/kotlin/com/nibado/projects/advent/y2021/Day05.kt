package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Line
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day05 : Day {
    private val values = resourceLines(2021, 5).map { it.split(" -> ")
        .map { it.split(',').map { it.toInt() }.let { (x,y) -> Point(x,y) } }
        .let { (start, end) -> Line(start, end) }
    }

    private fun List<Line>.solve() = fold(mutableMapOf<Point, Int>()) {
        acc, line -> line.points().forEach { acc[it] = acc.getOrDefault(it, 0) + 1 };acc
    }.count { (_,v) -> v > 1 }

    override fun part1() = values.filterNot { it.direction() == null }.solve()
    override fun part2() = values.solve()
}