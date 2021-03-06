package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day05 : Day {
    val lines = resourceLines(2017, 5).map {
        it.toInt()
    }.toList()

    override fun part1() = walk { 1 }.toString()
    override fun part2() = walk { if (it >= 3) -1 else 1 }.toString()

    fun walk(inc: (Int) -> Int): Int {
        val list = lines.toMutableList()
        var index = 0
        var step = 0
        while (true) {
            val to = index + list[index]

            list[index] += inc(list[index])

            index = to

            step++

            if (index >= lines.size || index < 0) {
                return step
            }
        }
    }
}