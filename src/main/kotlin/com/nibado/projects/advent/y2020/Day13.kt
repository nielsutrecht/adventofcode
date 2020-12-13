package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day13 : Day {
    private val lines = resourceLines(2020, 13)
    private val timeStamp = lines.first().toInt()
    private val busIds = lines.last().split(",").toList()

    override fun part1(): Int = busIds.filterNot { it == "x" }.map { it.toInt() }
            .map { it to timeStamp - (timeStamp % it) + it }
            .minBy { it.second - timeStamp }!!
            .let { it.first * (it.second - timeStamp) }

    override fun part2(): Long {
        val busses = busIds.mapIndexed { i, id -> id to i }
                .filterNot { it.first == "x" }.map { (a, b) -> a.toInt() to b }

        var subList = busses.take(2)

        var time = 0L
        var increment = subList.first().first.toLong()

        while(true) {
            time += increment

            if(subList.all { (time + it.second) % it.first == 0L }) {
                if(subList.size == busses.size) {
                    break
                } else {
                    increment = subList.map { it.first.toLong() }.reduce { a, b -> a * b }
                    subList = busses.take(subList.size + 1)
                }
            }
        }

        return time
    }
}
