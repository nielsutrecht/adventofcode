package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day04 : Day {
    val lines = resourceLines(2017, 4).map {
        it.split(" ").toList()
    }.toList()

    override fun part1() =
            lines.filter { it.size == it.toSet().size }.count().toString()

    override fun part2() =
            lines.map { it.map { it.toList().sorted().toString() } }.filter { it.size == it.toSet().size }.count().toString()
}