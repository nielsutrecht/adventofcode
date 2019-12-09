package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day09 : Day {
    val program = resourceString(2019, 9).split(",").map { it.toLong() }

    override fun part1() = TODO()

    override fun part2() = TODO()


}

fun main() {
    val c = IntCode(listOf(109, 1, 204, -1, 1001, 100, 1, 100, 1008, 100, 16, 101, 1006, 101, 0, 99), listOf())
    c.run()
    println(c.output)
}