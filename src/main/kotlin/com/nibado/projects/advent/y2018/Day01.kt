package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day01 : Day {
    private val numbers = resourceLines(2018, 1).map { it.toInt() }

    override fun part1() = numbers.sum()

    override fun part2() : Int {
        val set = mutableSetOf<Int>()
        var freq = 0

        while(true) {
            for (i in numbers) {
                freq += i

                if (set.contains(freq)) {
                    return freq
                }

                set += freq
            }
        }
    }
}