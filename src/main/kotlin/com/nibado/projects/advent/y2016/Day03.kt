package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day03 : Day {
    private val input = resourceLines(2016, 3).map { it.trim().split(Regex("\\s+")).map { it.toInt() }.toList() }.toList()

    override fun part1() = input.filter(Day03::valid).count().toString()
    override fun part2() = byColumns(input).filter(Day03::valid).count().toString()

    private fun byColumns(triangles: List<List<Int>>) =
            (0 until triangles.size step 3).flatMap { a -> (0..2).map { Pair(a, it) } }
                    .map { listOf(triangles[it.first][it.second], triangles[it.first + 1][it.second], triangles[it.first + 2][it.second]) }

    private fun valid(tr: List<Int>) = tr[0] + tr[1] > tr[2] && tr[0] + tr[2] > tr[1] && tr[1] + tr[2] > tr[0]
}