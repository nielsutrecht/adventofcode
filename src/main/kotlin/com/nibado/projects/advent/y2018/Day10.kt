package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.Rectangle
import com.nibado.projects.advent.resourceLines
import kotlin.math.max
import kotlin.math.min

object Day10 : Day {
    private val input = resourceLines(2018, 10).map(::parse)
    private val solution: Pair<String, Int> by lazy { solve() }

    private fun parse(line: String) = line.split("[^0-9-]+".toRegex())
            .filterNot { it.trim().isEmpty() }.map { it.toInt() }
            .let { (x, y, dx, dy) -> Star(Point(x, y), Point(dx, dy)) }

    private fun toString(stars: List<Star>): String {
        val points = stars.map { it.loc }.toSet()
        val rect = Rectangle.containing(points)
        val builder = StringBuilder()

        for (y in rect.left.y..rect.right.y) {
            for (x in rect.left.x..rect.right.x) {
                builder.append(if (points.contains(Point(x, y))) {
                    '#'
                } else {
                    '.'
                })
            }
            builder.append('\n')
        }

        return builder.toString()
    }

    private fun area(stars: List<Star>) = stars
            .fold(listOf(Int.MAX_VALUE, Int.MAX_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)) { (minX, minY, maxX, maxY), s ->
                listOf(min(minX, s.loc.x), min(minY, s.loc.y), max(maxX, s.loc.x), max(maxY, s.loc.y))
            }
            .let { (minX, minY, maxX, maxY) -> (maxX - minX).toLong() * (maxY - minY).toLong() }

    private fun solve(): Pair<String, Int> {
        var stars = input

        var second = 0
        var area = Long.MAX_VALUE

        while (true) {
            val next = stars.map { it.copy(loc = it.loc + it.velocity) }
            val nextArea = area(next)
            if (area < nextArea) {
                break
            }
            second++
            stars = next
            area = nextArea
        }
        return toString(stars) to second
    }

    override fun part1() = Day10Ocr.ocr(solution.first)
    override fun part2() = solution.second

    data class Star(val loc: Point, val velocity: Point)
}