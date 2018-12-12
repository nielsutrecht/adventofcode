package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day12 : Day {
    private val regex = "([#.]{5}) => (#|.)".toRegex()
    private val input = resourceLines(2018, 12)
    private val instructions = input.drop(2).map(::parse).toMap()
    private val initial = input.first().removePrefix("initial state: ")

    private fun parse(line: String) = regex.matchEntire(line)?.groupValues?.drop(1)
            ?.let { it[0] as CharSequence to it[1].first() } ?: throw IllegalArgumentException()

    private fun next(current: Pair<Int, String>) : Pair<Int, String> {
        val builder = StringBuilder(current.second)

        val dotsStart = with(builder) {
            val dotsStart = 5 - (0 .. 4).takeWhile { this[it] == '.' }.count()
            val dotsEnd = 5 - (1 .. 5).takeWhile { this[this.length - it] == '.' }.count()

            insert(0, ".".repeat(dotsStart))
            append(".".repeat(dotsEnd))

            dotsStart
        }

        return dotsStart + current.first to (sequenceOf ('.', '.') + (2 .. builder.length - 3)
                .asSequence()
                .map { instructions.getOrDefault(builder.subSequence(it - 2, it + 3), '.') })
                .joinToString("")
    }

    private fun simulate(state: Pair<Int, String>, times: Int) : Pair<Int, String> = (1 .. times).fold(state) { a, _ -> next(a)}
    private fun count(current: Pair<Int, String>) = current.second.mapIndexed { index, c -> index - current.first to c }
            .sumBy { if(it.second == '#') it.first else 0 }

    override fun part1() = count(simulate(0 to initial, 20))

    override fun part2(): Long {
        val state100 = simulate(0 to initial, 100)
        val state200 = simulate(state100, 100)

        val c1 = count(state100)
        val d = count(state200) - c1

        return (50000000000 - 100) / 100L * d + c1
    }
}