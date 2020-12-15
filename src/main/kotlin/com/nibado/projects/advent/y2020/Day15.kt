package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day15 : Day {
    private val values = listOf(0, 20, 7, 16, 1, 18, 15)

    override fun part1(): Int = solve(2020)
    override fun part2(): Int = solve(30000000)

    private fun solve(turns: Int): Int {
        val indexMap = mutableMapOf<Int, MutableList<Int>>()

        fun add(turn: Int, value: Int) = indexMap
                .computeIfAbsent(value) { mutableListOf() }
                .add(turn)

        values.forEachIndexed { index, i -> add(index + 1, i) }

        var last = values.last()
        var turn = values.size + 1
        while (turn <= turns) {
            val indices = indexMap.getValue(last).takeLast(2)

            last = if (indices.size == 1) 0 else indices[1] - indices[0]

            add(turn, last)

            turn++
        }

        return last
    }
}
