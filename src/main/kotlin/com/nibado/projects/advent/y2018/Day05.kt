package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day05 : Day {
    private val input = resourceString(2018, 5)

    override fun part1() = react(input)

    override fun part2() =
        input.map { it.toLowerCase() }.toSet()
                .map { c -> c to input.filter { it.toLowerCase() != c } }
                .map { it.first to react(it.second) }
                .minBy { it.second }!!.second

    private fun react(inp: String) : Int {
        val poly = StringBuilder(inp)

        var i = 0
        while(i < poly.length - 1) {
            if(Math.abs(poly[i] - poly[i + 1]) == 32) {
                poly.delete(i, i + 2)
                if(i > 0) {
                    i--
                }
            } else {
                i++
            }
        }

        return poly.length
    }
}