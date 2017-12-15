package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex

object Day15 : Day {
    val input = resourceRegex(2016, 15, Regex("Disc #[0-9]+ has ([0-9]+) positions; at time=0, it is at position ([0-9]+).")).map { it[1].toInt() to it[2].toInt() }

    fun pos(disk: Pair<Int, Int>, time: Int) = (disk.second + time) % disk.first

    fun solve(disks: List<Pair<Int, Int>>) = (0 .. Int.MAX_VALUE).find { time ->
        disks.mapIndexed { i, d -> pos(d, time + i) }.all { it == 0 }
    }!! - 1

    override fun part1() = solve(input).toString()
    override fun part2() = solve(input + Pair(11,  0)).toString()
}