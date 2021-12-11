package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day11 : Day {
    private val values = resourceLines(2021, 11).map { it.toCharArray().map { it.digitToInt() } }
    private val points = values.indices.flatMap { y -> values[0].indices.map { x -> Point(x, y) } }

    override fun part1() : Int {
        val grid = values.map { it.toMutableList() }
        var flashCount = 0
        println("Before any steps:")
        printGrid(grid)
        repeat(100) {
            val flashed = mutableSetOf<Point>()
            points.forEach { (x,y) -> grid[y][x] += 1}
            points.forEach { p ->
                if(grid[p.y][p.x] > 9) {
                    flash(grid, p, flashed)
                }
            }
            flashed.forEach { (x, y) -> grid[y][x] = 0 }
            //println("\nAfter step ${it + 1}:")
            //printGrid(grid)
            flashCount += flashed.size
        }
        return flashCount
    }

    private fun printGrid(grid: List<MutableList<Int>>) {
        println(grid.joinToString("\n") { it.joinToString("") })
    }

    private fun flash(grid: List<MutableList<Int>>, p: Point, flashed: MutableSet<Point>) {
        flashed += p
        grid[p.y][p.x] = 0
        val neighbors = p.neighbors().filter { it.inBound(values[0].size - 1, values.size - 1) }
        neighbors.map { n ->
            grid[n.y][n.x] += 1
            if(grid[n.y][n.x] > 9 && n !in flashed) {
                flash(grid, n, flashed)
            }
        }
    }

    override fun part2() : Int {
        val grid = values.map { it.toMutableList() }
        var flashCount = 0
        println("Before any steps:")
        printGrid(grid)
        var step = 1
        while(true) {
            val flashed = mutableSetOf<Point>()
            points.forEach { (x,y) -> grid[y][x] += 1}
            points.forEach { p ->
                if(grid[p.y][p.x] > 9) {
                    flash(grid, p, flashed)
                }
            }
            flashed.forEach { (x, y) -> grid[y][x] = 0 }
            //println("\nAfter step ${it + 1}:")
            //printGrid(grid)
            flashed.size
            if(flashed.size == values.size * values[0].size) {
                return step
            }
            step++
        }
    }
}

fun main() {
    println(Day11.part2())
}