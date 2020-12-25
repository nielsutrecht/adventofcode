
package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day25 : Day {
    private val input = resourceLines(2020, 25).map { it.toLong() }

    override fun part1() : Long {
        var a = 1L
        var b = 1L

        while(true) {
            a = (a * 7L) % 20201227L
            b = (b * input[1]) % 20201227L
            if(a == input[0]) {
                return b
            }
        }
    }

    override fun part2() = 0
}