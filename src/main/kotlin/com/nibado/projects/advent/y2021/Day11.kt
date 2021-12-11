package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day11 : Day {
    private val grid = resourceLines(2021, 11).map { it.map { it.digitToInt() } }.map { it.toMutableList() }
    private val points = grid.indices.flatMap { y -> grid[0].indices.map { x -> Point(x, y) } }
    private val solution = solve()

    fun solve(): List<Pair<Int, Int>> {
        val result = mutableListOf<Pair<Int, Int>>()
        var step = 1
        while (true) {
            val flashed = mutableSetOf<Point>()
            points.forEach { p ->
                grid[p.y][p.x] += 1
                if (grid[p.y][p.x] > 9) {
                    flash(grid, p, flashed)
                }
            }
            flashed.forEach { (x, y) -> grid[y][x] = 0 }
            result += step to flashed.size
            if (flashed.size == grid.size * grid[0].size) {
                break
            }
            step++
        }
        return result
    }

    private fun flash(grid: List<MutableList<Int>>, p: Point, flashed: MutableSet<Point>) {
        flashed += p
        grid[p.y][p.x] = 0
        val neighbors = p.neighbors().filter { it.inBound(grid[0].size - 1, grid.size - 1) }
        neighbors.map { n ->
            grid[n.y][n.x] += 1
            if (grid[n.y][n.x] > 9 && n !in flashed) {
                flash(grid, n, flashed)
            }
        }
    }

    override fun part1() = solution.filter { it.first <= 100 }.sumOf { it.second }
    override fun part2() = solution.last().first
}