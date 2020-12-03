package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.CharMap

object Day03 : Day {
    private val values = resourceLines(2020, 3)

    override fun part1(): Long = count(Point(3, 1))
    override fun part2() = listOf(Point(1, 1), Point(3, 1), Point(5, 1),
            Point(7, 1), Point(1, 2)).map(::count).reduce { a, b -> a * b }

    private fun count(increment: Point) : Long = ray(increment)
            .takeWhile { it.y < values.size }
            .count { values[it.y][it.x % values.first().length] == '#' }.toLong()

    private fun ray(increment: Point) = sequence {
        var current = Point(0, 0)
        while(true) {
            current += increment
            yield(current)
        }
    }
}
