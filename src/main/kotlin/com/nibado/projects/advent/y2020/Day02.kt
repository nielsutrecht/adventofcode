package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex

object Day02 : Day {
    private val values = resourceRegex(2020, 2, "([0-9]+)-([0-9]+) ([a-z]): ([a-z]+)")
            .map { (_, min, max, c, pass) -> Row(min.toInt(), max.toInt(), c[0], pass) }

    override fun part1() = values.count { it.isMatch1() }
    override fun part2() = values.count { it.isMatch2() }

    data class Row(val min: Int, val max: Int, val c: Char, val pass: String) {
        fun isMatch1(): Boolean = pass.count { it == c } in min..max
        fun isMatch2(): Boolean = (pass[min - 1] == c || pass[max - 1] == c) && pass[min - 1] != pass[max - 1]
    }
}

