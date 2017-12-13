package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day13 : Day {
    private val input = resourceLines(13).map { it.split(": ") }.map { Pair(it[0].toInt(), it[1].toInt()) }.toMap()

    override fun part1() = input.entries
            .map { if (it.key % (2 * (it.value - 1)) == 0) it.key * it.value else 0 }
            .sum().toString()

    override fun part2() : String {
        var delay = 0
        while (input.entries.filter { (it.key + delay) % (2 * (it.value - 1)) == 0 }.isNotEmpty())
            delay++

        return delay.toString()
    }
}