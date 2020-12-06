package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day06 : Day {
    private val values = resourceString(2020, 6).split("\n\n").map { it.trim() }

    override fun part1() = values.map { group -> group.filter { it in 'a' .. 'z' }.toSet().size }.sum()
    override fun part2() = values.map { group ->
        group.split("\n").map { it.toSet() }.reduce { a, b -> a.intersect(b) }.size
    }.sum()
}
