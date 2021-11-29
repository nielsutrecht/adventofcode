package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.SummedAreaTable
import kotlin.math.min

object Day11 : Day {
    private val grid = Grid(6392)

    class Grid(serial: Int) {
        private val grid = IntArray(300 * 300)
        private val summed: SummedAreaTable

        init {
            for (x in 1..300) {
                for (y in 1..300) {
                    val rackId = x + 10
                    grid[toIndex(x, y)] = hundred((rackId * y + serial) * rackId) - 5
                }
            }

            summed = SummedAreaTable.from(grid.toList().chunked(300).map { it.toIntArray() })
        }

        fun get(x: Int, y: Int, size: Int = 3): Int {
            val a = Point(x - 1, y - 1)
            val b = a + Point(size - 1, size - 1)

            return summed.get(a, b)
        }

        private fun hundred(v: Int) = ((v % 1000) - (v % 100)) / 100
        private fun toIndex(x: Int, y: Int) = x - 1 + (y - 1) * 300
    }

    private fun maxSize(p: Point) = min(300 - p.x, 300 - p.y)

    override fun part1() = (1..298).flatMap { y -> (1..298).map { x -> Point(x, y) } }
            .maxByOrNull { grid.get(it.x, it.y) }
            ?.let { "${it.x},${it.y}" }!!

    override fun part2() = (1..300).flatMap { y -> (1..300).map { x -> Point(x, y) } }.asSequence()
                .flatMap { p -> (1..maxSize(p)).asSequence().map { p to it } }
                .maxByOrNull { grid.get(it.first.x, it.first.y, it.second) }
                ?.let { "${it.first.x},${it.first.y},${it.second}" }!!
}