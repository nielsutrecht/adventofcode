package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day08 : Day {
    private val input = resourceString(2019, 8).map { it - '0' }.toList()

    override fun part1() = input.chunked(25 * 6)
            .minBy { it.count { it == 0 } }
            ?.run { count { it == 1 } * count {it == 2}  }!!

    override fun part2(): String {
        val layers = input.chunked(25 * 6)

        val grid = IntArray(25 * 6)
        grid.fill(-1)

        layers.forEach { layer ->
            layer.forEachIndexed { index, i ->
                if(grid[index] in listOf(-1, 2)) {
                    grid[index] = i
                }
            }
        }

        return grid.map { if(it == 1) 'X' else ' '}.chunked(25).map { it.joinToString("") }.joinToString("\n")
    }
}