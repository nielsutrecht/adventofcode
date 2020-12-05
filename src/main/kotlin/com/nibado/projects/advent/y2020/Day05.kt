package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day05 : Day {
    private val seats: Set<Int> by lazy { resourceLines(2020, 5).map(::seat).toSet() }

    override fun part1(): Int = seats.max()!!
    override fun part2(): Int = (seats.min()!!..seats.max()!!).first { !seats.contains(it) }

    private fun seat(pass: String): Int = pass.fold(0..127 to 0..7) { (row, col), c ->
        when (c) {
            'F' -> row.split().let { (f, _) -> f } to col
            'B' -> row.split().let { (_, b) -> b } to col
            'R' -> row to col.split().let { (_, r) -> r }
            'L' -> row to col.split().let { (l, _) -> l }
            else -> row to col
        }
    }.let { (row, col) -> row.first * 8 + col.first }
}
