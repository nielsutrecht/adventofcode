package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import com.nibado.projects.advent.split

object Day05 : Day {
    private val seats = resourceLines(2020, 5).map(::seat).toSet()

    override fun part1() = seats.max()!!

    override fun part2() : Long =
        (seats.min()!! .. seats.max()!!).first { !seats.contains(it) }

    fun seat(pass: String) : Long {
        var row = 0 .. 127
        var column = 0 .. 7

        for(c in pass) {
            when(c) {
                'F' -> row = row.split().let { (f, _) -> f }
                'B' -> row = row.split().let { (_, b) -> b }
                'R' -> column = column.split().let { (_, r) -> r }
                'L' -> column = column.split().let { (l, _) -> l }
            }
        }

        return row.first * 8L + column.first
    }
}
