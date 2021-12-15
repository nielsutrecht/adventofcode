package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.NumberGrid
import com.nibado.projects.advent.search.Dijkstra

object Day15 : Day {
    private val grid = resourceLines(2021, 15).let { NumberGrid.from<Int>(it) }

    private fun search(grid: NumberGrid<Int>) = Dijkstra.shortestPath(grid, grid.bounds.left, grid.bounds.right)
        .sumOf { grid[it] } - grid[Point.ORIGIN]

    override fun part1() =  search(grid)
    override fun part2() : Int {
        val tiled = NumberGrid(grid.width * 5, grid.height * 5, 0)
        grid.points.forEach {
            (0 .. 4).forEach { scaleY ->
                (0..4).forEach { scaleX ->
                    val newPoint = Point(it.x + (scaleX * grid.width), it.y + (scaleY * grid.height))
                    val newValue = (grid[it] + scaleX + scaleY).let { if(it > 9) it - 9 else it }
                    tiled[newPoint] = newValue
                }
            }
        }
        return search(tiled)
    }
}