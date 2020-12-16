package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day01 : Day {
    private val instructions = resourceString(2015, 1).map { if (it == '(') 1 else -1 }

    override fun part1() = instructions.sum()
    override fun part2(): Int {
        var sum = 0
        var idx = 0
        instructions.mapIndexed { index, i -> index to i }.takeWhile { sum += it.second; idx = it.first;sum >= 0 }

        return idx + 1
    }
}
