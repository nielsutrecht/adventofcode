package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.combine
import com.nibado.projects.advent.resourceLines

object Day20 : Day {
    private val input = resourceLines(2016, 20).map { it.split("-") }.map { it[0].toLong() .. it[1].toLong() }.sortedBy { it.first }
    private val ranges: List<LongRange> by lazy { combine(input) }

    override fun part1() = (ranges[0].last + 1).toString()
    override fun part2() = (0 until ranges.size - 1).sumBy { (ranges[it + 1].first - ranges[it].last - 1).toInt() }.toString()
}
