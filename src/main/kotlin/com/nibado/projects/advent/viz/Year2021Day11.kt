package com.nibado.projects.advent.viz

import com.nibado.projects.advent.*
import com.nibado.projects.advent.graphics.*
import java.awt.Color
import java.io.File

object Year2021Day11 {
    private val grid = resourceLines(2021, 11).map { it.map { it.digitToInt() } }.map { it.toMutableList() }
    private val points = grid.indices.flatMap { y -> grid[0].indices.map { x -> Point(x, y) } }
    private val gif = AnimatedGif(0, 1)

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
            draw(flashed)
            if (flashed.size == grid.size * grid[0].size) {
                break
            }
            step++
        }
        return result
    }

    private fun draw(flashed: Set<Point>) {
        val img = Images.create(grid[0].size * CELL_WIDTH, grid.size * CELL_WIDTH)

        points.forEach { p ->
            val (x, y) = p
            val g = img.graphics
            val value = grid[y][x]
            val color = if(p !in flashed) {
                COLOR_MAP[value]
            } else {
                Color.YELLOW
            }
            g.color = color
            g.fillRect(x * CELL_WIDTH, y * CELL_WIDTH,
                CELL_WIDTH,
                CELL_WIDTH
            )
        }

        gif += img
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

    fun write(file: File) {
        gif.encodeTo(file)
    }

    private val COLOR_MAP = (0..9).map { it to 100 + it * 15 }.associate { (i, c) -> i to Color(c, c, c) }
    private const val CELL_WIDTH = 20
}

fun main() {
    Year2021Day11.solve()
    Year2021Day11.write(File("target/aniY2021D11.gif"))
}