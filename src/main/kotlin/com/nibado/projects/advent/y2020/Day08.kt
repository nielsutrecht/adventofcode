package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day08 : Day {
    private val program = resourceLines(2020, 8).map { it.split(" ").let { (a, b) -> a to b.toInt() } }

    override fun part1() = run(-1).first
    override fun part2(): Int = program.asSequence().mapIndexedNotNull { i, (inst, _) -> if (inst != "acc") i else null }
            .map { run(it) }.first { !it.second }.let { it.first }

    private fun run(swap: Int): Pair<Int, Boolean> {
        var acc = 0
        var ip = 0
        val locations = mutableSetOf<Int>()

        while (true) {
            if (ip >= program.size) {
                return acc to false
            }
            if (locations.contains(ip)) {
                return acc to true
            }
            locations += ip
            val inst = if (ip == swap) {
                if (program[ip].first == "jmp") "nop" else "jmp"
            } else program[ip].first

            when (inst) {
                "acc" -> {
                    acc += program[ip].second; ip++
                }
                "nop" -> ip++
                "jmp" -> ip += program[ip].second
            }
        }
    }
}
