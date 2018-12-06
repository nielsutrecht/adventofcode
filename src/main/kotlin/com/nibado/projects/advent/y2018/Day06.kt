package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.Rectangle
import com.nibado.projects.advent.resourceLines
import java.util.*

object Day06 : Day {
    private val input = resourceLines(2018, 6).map { it.split(", ").map { it.toInt() } }.map { Point(it[0], it[1]) }
    private val rect = Rectangle(Point(input.minBy { it.x }!!.x - 1, input.minBy { it.y }!!.y - 1), Point(input.maxBy { it.x }!!.x + 1, input.maxBy { it.y }!!.y + 1))

    override fun part1(): Int {
        val nearest = rect.points().map { p -> p to nearest(p) }

        val grouped = nearest.filterNot { it.second == null }.groupBy { it.second!! }

        val grouped2 = grouped.filterNot { it.value.map { it.first }.any { rect.onEdge(it) } }.maxBy { it.value.size }!!

        return grouped2.value.size
    }

    override fun part2(): Int {
        val areaSet = rect.points().map { p -> p to distanceSum(p) }.filter { it.second < 10000 }.map { it.first }.toSet()

        val result = floodFill(areaSet).sortedBy { it.size }

        result.forEachIndexed { index, set -> println("$index: ${set.size}") }

        return result.first().size
    }

    private fun distanceSum(p : Point) : Int {
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

    private fun floodFill(set: Set<Point>) : List<Set<Point>> {
        val visited = mutableSetOf<Point>()
        val result : MutableList<Set<Point>> = mutableListOf()

        while(set.size > visited.size) {
            var area = mutableSetOf<Point>()

            result += area

            val stack = Stack<Point>()
            stack += set.filterNot { visited.contains(it) }.first()

            while(!stack.empty()) {
                val current = stack.pop()
                area.add(current)
                visited.add(current)

                stack += current.neighbors().filter { set.contains(it) && !visited.contains(it) }
            }
        }

        return result
    }
}