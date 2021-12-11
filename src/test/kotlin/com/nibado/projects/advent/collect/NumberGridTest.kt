package com.nibado.projects.advent.collect

import com.nibado.projects.advent.*
import org.junit.jupiter.api.Test

internal class NumberGridTest {
    @Test
    fun readGrid() {
        val lines = resourceLines(2021, 11)

        val grid = NumberGrid.from<Int>(lines)
        println(grid)
        println()
        grid.apply { _, e -> e + 1 }
        println(grid)
        println()
        grid[0,0] += 1
        println(grid)
        println()
        grid[Point.ORIGIN] += 1
        println(grid)
        println()

        for((p, n) in grid) {
            println("$p: $n")
        }
    }
}