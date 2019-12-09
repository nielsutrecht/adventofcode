package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day09 : Day {
    private val program = resourceString(2019, 9).split(",").map { it.toLong() }

    override fun part1() =IntCode(program, listOf(1)).also { it.run() }.output.last()!!
    override fun part2() = IntCode(program, listOf(2)).also { it.run() }.output.last()!!
}