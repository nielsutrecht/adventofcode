package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day05 : Day {
    private val input = resourceString(2019, 5).split(",").map { it.toLong() }

    override fun part1(): Long = IntCode(input.toMutableList(), mutableListOf(1)).also { it.run() }.output.last()
    override fun part2(): Long = IntCode(input.toMutableList(), mutableListOf(5)).also { it.run() }.output.last()
}