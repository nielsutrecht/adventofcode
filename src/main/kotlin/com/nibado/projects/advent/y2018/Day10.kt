package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.Rectangle
import com.nibado.projects.advent.resourceLines

object Day10 : Day {
    private val input = resourceLines(2018, 10).map(::parse)
    private val solution: Pair<String, Int> by lazy { solve() }

    private fun parse(line: String): Star {
        val (x, y, dx, dy) = line.split("[^0-9-]+".toRegex()).filterNot { it.trim().isEmpty() }.map { it.toInt() }

        return Star(Point(x, y), Point(dx, dy))
    }

    private fun toString(stars: List<Star>) : String {
        val points = stars.map { it.loc }.toSet()
        val rect = Rectangle.containing(points)
        val builder = StringBuilder()

        for (y in rect.left.y..rect.right.y) {
            for (x in rect.left.x..rect.right.x) {
                builder.append(if (points.contains(Point(x, y))) { '#' } else { '.' })
            }
            builder.append('\n')
        }

        return builder.toString()
    }

    private fun area(stars: List<Star>)  : Long {
        var minX = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var minY = Int.MAX_VALUE
        var maxY = Int.MIN_VALUE

        stars.forEach {
            minX = Math.min(minX, it.loc.x)
            maxX = Math.max(maxX, it.loc.x)
            minY = Math.min(minY, it.loc.y)
            maxY = Math.max(maxY, it.loc.y)
        }

        return (maxX - minX).toLong() * (maxY - minY).toLong()
    }

    private fun solve() : Pair<String, Int> {
        var stars = input

        var second = 0
        var area = Long.MAX_VALUE

        while(true) {
            val next = stars.map { it.tick() }
            val nextArea = Day10.area(next)
            if(area < nextArea) {
                break
            }
            second++
            stars = next
            area = nextArea
        }
        return toString(stars) to second
    }

    override fun part1() = solution.first
    override fun part2() = solution.second

    data class Star(val loc: Point, val velocity: Point) {
        fun tick() = Star(loc + velocity, velocity)
    }
}