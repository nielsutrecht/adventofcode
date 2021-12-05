package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day01 : Day {
    private val values = resourceLines(2021, 1).map { it.toInt() }

    override fun part1() = values.windowed(2).count { (a, b) -> a < b }

    override fun part2() = values
            .windowed(3).map { it.sum() }
            .windowed(2).count { (a, b) -> a < b }
}