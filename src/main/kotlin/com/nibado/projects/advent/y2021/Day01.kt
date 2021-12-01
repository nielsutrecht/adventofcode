package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day01 : Day {
    private val values = resourceLines(2021, 1).map { it.toInt() }

    override fun part1() = values.windowed(2).sumOf { (a, b) -> if (a < b) 1L else 0L }

    override fun part2() = values
        .windowed(3).map { it.sum() }
        .windowed(2).sumOf { (a, b) -> if (a < b) 1L else 0L }
}