package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day08 : Day {
    private val input = resourceString(2019, 8).map { it - '0' }.toList()

    override fun part1(): Any {
        //25 x 6
        val layers = input.chunked(25 * 6)
        val min = layers.minBy { it.count { it == 0 } }!!
        return min.count { it == 1 } * min.count { it == 2 }
    }

    override fun part2(): IntArray {
        val layers = input.chunked(25 * 6)

        val grid = IntArray(25 * 6)
        grid.fill(-1)

        layers.forEach { layer ->
            layer.forEachIndexed { index, i ->
                when {
                    grid[index] == -1 -> grid[index] = i
                    grid[index] == 2 -> grid[index] = i
                }
            }
        }

        (0 until 6).forEach { y ->
            (0 until 25).forEach { x ->
                val z = if(grid[y * 25 + x] == 1) 'X' else ' '
                print(z)
            }
            println()
        }

        return grid

    }
}

fun main() {
    println(Day08.part2())

}