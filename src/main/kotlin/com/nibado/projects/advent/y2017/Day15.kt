package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day

object Day15 : Day {
    override fun part1() = solve(40_000_000, Generator(699, 16807), Generator(124, 48271))
    override fun part2() = solve(5_000_000, Generator(699, 16807, 4), Generator(124, 48271, 8))

    private fun solve(iter: Int, a: Generator, b: Generator) = (1 .. iter).count { a.next().toInt() and 0x0000FFFF == b.next().toInt() and 0x0000FFFF }.toString()

    class Generator(var current: Long, val factor: Int, val multiples: Int = 1) {
        fun next(): Long {
            do {
                current = current * factor % 2147483647L
            } while(current % multiples != 0L)

            return current
        }
    }
}