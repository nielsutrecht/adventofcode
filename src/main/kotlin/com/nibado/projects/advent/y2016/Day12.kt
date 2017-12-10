package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day12 : Day {
    private val input = resourceLines(2016, 12).map { it.split(" ").map { it.trim() } }

    override fun part1() = run()
    override fun part2() = run(mutableMapOf(Pair("c", 1)))

    private fun run(registers: MutableMap<String, Int> = mutableMapOf()) : String {
        fun get(reg: String) = registers.computeIfAbsent(reg, {0})

        var index = 0
        while(index < input.size) {
            val cmd = input[index]
            index++
            when(cmd[0]) {
                "cpy" -> registers[cmd[2]] = cmd[1].toIntOrNull() ?: get(cmd[1])
                "inc" -> registers[cmd[1]] = get(cmd[1]) + 1
                "dec" -> registers[cmd[1]] = get(cmd[1]) - 1
                "jnz" -> {
                    val value = cmd[1].toIntOrNull() ?: get(cmd[1])
                    if(value != 0) {
                        index += cmd[2].toInt() - 1
                    }
                }
            }
        }

        return registers["a"]?.toString() ?: ""
    }
}