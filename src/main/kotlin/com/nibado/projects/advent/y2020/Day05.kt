package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day05 : Day {
    private val seats: Set<Int> by lazy { resourceLines(2020, 5).map(::seat).toSet() }

    override fun part1(): Int = seats.max()!!
    override fun part2(): Int = (seats.min()!!..seats.max()!!).first { !seats.contains(it) }

    private fun seat(pass: String) = pass.fold(0) { n, c -> (n shl 1) + (if (c == 'B' || c == 'R') 1 else 0) }
}
