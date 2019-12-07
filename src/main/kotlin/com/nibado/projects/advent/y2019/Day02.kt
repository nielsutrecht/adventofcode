package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString

object Day02 : Day {
    const val ADD = 1
    const val MUL = 2
    const val TERM = 99
    private val opcodes = resourceString(2019, 2).split(",").map { it.toInt() }

    override fun part1() = run(12, 2)
    override fun part2() = (0..99).flatMap { noun -> (0..99).map { verb -> noun to verb } }
        .first { (noun, verb) -> run(noun, verb) == 19690720 }.let { (noun, verb) -> 100 * noun + verb }

    private fun run(noun: Int, verb: Int): Int {
        val memory = opcodes.toMutableList()

        memory[1] = noun
        memory[2] = verb

        var index = 0
        while (true) {
            when (memory[index]) {
                ADD -> memory[memory[index + 3]] = memory[memory[index + 1]] + memory[memory[index + 2]]
                MUL -> memory[memory[index + 3]] = memory[memory[index + 1]] * memory[memory[index + 2]]
                TERM -> return memory[0]
            }

            index += 4
        }
    }
}