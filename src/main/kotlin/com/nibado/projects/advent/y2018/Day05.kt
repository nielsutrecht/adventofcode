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

        while(true) {
            var changes = false

            for(i in (0 until poly.length - 1)) {
                if(poly[i].toLowerCase() == poly[i + 1].toLowerCase() && poly[i] != poly[i + 1]) {
                    poly.delete(i, i + 2)
                    changes = true
                    break
                }
            }

            if(!changes) {
                break
            }
        }

        return poly.length
    }
}