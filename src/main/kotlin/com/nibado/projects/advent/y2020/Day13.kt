package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day13 : Day {
    private val lines = resourceLines(2020, 13)
    private val timeStamp = lines.first().toInt()
    private val busIds = lines.last().split(",").toList()

    override fun part1(): Int = busIds.filterNot { it == "x" }.map { it.toInt() }
            .map { it to timeStamp - (timeStamp % it) + it }
            .minByOrNull { it.second - timeStamp }!!
            .let { it.first * (it.second - timeStamp) }

    override fun part2(): Long {
        val busses = busIds.mapIndexed { i, id -> id to i }
                .filterNot { it.first == "x" }.map { (a, b) -> a.toInt() to b }

        var time = 0L
        var increment = 1L

        for((id, i) in busses) {
            while((time + i) % id != 0L) {
                time += increment
            }
            increment *= id
        }

        return time
    }
}
