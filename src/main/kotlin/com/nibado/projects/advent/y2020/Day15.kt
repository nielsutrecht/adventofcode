package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Day

object Day15 : Day {
    private val values = listOf(0, 20, 7, 16, 1, 18, 15)

    override fun part1(): Int = solve(2020)
    override fun part2(): Int = solve(30000000)

    private fun solve(turns: Int): Int {
        val indexArray = Array(turns * 2) { -1 }
        var last = values.last()

        values.forEachIndexed { index, i -> indexArray[i * 2 + 1] = index + 1 }

        for (turn in values.size + 1 .. turns) {
            last = if (indexArray[last * 2] == -1) 0 else indexArray[last * 2 + 1] - indexArray[last * 2]

            indexArray[last * 2] = indexArray[last * 2 + 1]
            indexArray[last * 2 + 1] = turn
        }

        return last
    }
}
