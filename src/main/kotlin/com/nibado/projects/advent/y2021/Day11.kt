package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.NumberGrid

object Day11 : Day {
    private val grid = resourceLines(2021, 11).let { NumberGrid.from<Int>(it) }
    private val solution = solve()

    fun solve(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        var step = 1
        while (grid.elements.any { it != 0 }) {
            val flashed = mutableSetOf<Point>()
            grid.points.forEach { p ->
                grid[p] += 1
                if (grid[p] > 9) {
                    flash(p, flashed)
                }
            }
            flashed.forEach { p -> grid[p] = 0 }
            result += step++ to flashed.size
        }
        return result
    }

    private fun flash(p: Point, flashed: MutableSet<Point>) {
        flashed += p
        grid[p] = 0
        p.neighbors().filter(grid::inBound).forEach { n ->
            grid[n] += 1
            if (grid[n] > 9 && n !in flashed) {
                flash(n, flashed)
            }
        }
    }

    override fun part1() = solution.filter { it.first <= 100 }.sumOf { it.second }
    override fun part2() = solution.last().first
}