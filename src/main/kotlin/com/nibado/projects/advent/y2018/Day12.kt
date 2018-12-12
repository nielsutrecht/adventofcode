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

        var inserted = 0

        while(builder.subSequence(0, 6).contains('#')) {
            builder.insert(0, '.')
            inserted++
        }
        while(builder.subSequence(builder.length - 6, builder.length).contains('#')) {
            builder.append('.')
        }
        val newState = StringBuilder(builder)
        for(i in 2 .. builder.length - 3) {
            val seq = builder.subSequence(i - 2, i + 3)

            newState[i] =  instructions.getOrDefault(seq, '.')
        }

        return inserted + current.first to newState.toString()
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