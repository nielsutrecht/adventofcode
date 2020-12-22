package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.*

object Day05 : Day {
    private val values = resourceLines(2015, 5)
    private val vowels = "aeiou".toSet()
    private val bad = setOf("ab", "cd", "pq", "xy")

    override fun part1() = values.count { line ->
        line.count { it in vowels } >= 3 &&
        line.windowed(2,1).any {it -> it[0] == it[1]} &&
        bad.none { line.contains(it) }
    }
    override fun part2() = values.count { line ->
        line.windowed(2, 1).any { line.lastIndexOf(it) - line.indexOf(it) > 1 } &&
        line.indices.any { it + 2 in line.indices && line[it] == line[it + 2] }
    }
}