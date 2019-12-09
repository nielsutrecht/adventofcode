package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day02 : Day {
    private val program = resourceString(2019, 2).split(",").map { it.toLong() }

    override fun part1() = run(12,2)
    override fun part2() = (0..99).flatMap { noun -> (0..99).map { verb -> noun to verb } }
        .first { (noun, verb) -> run(noun, verb) == 19690720 }.let { (noun, verb) -> 100 * noun + verb }

    private fun run(noun: Int, verb: Int) : Int =
        IntCode(program).also { it.memory.set(1, noun.toLong());it.memory.set(2, verb);it.run() }.memory.get(0).toInt()
}