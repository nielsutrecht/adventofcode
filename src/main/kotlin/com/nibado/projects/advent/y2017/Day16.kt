package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.*

object Day16 : Day {
    val input = resourceString(16).split(",").map { it[0] to it.substring(1).split("/") }

    override fun part1() = solve(1)
    override fun part2() = solve(1_000_000_000)

    fun solve(times: Int): String {
        var programs = ('a'..'p').toList()
        val seen = mutableSetOf<List<Char>>()
        var target = times
        var i = 0

        while(i < target) {
            input.forEach {
                programs = when (it.first) {
                    's' -> rotateRight(programs, it.second.first().toInt())
                    'x' -> swap(programs, it.second[0].toInt(), it.second[1].toInt())
                    'p' -> swapByValue(programs, it.second[0][0], it.second[1][0])
                    else -> programs
                }
            }

            if(seen.contains(programs) && target == times) {
                target = target % i + i
            } else {
                seen += programs.toList()
            }
            i++
        }

        return programs.joinToString("")
    }
}