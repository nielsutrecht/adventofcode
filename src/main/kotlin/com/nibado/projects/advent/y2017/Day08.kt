package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex

object Day08 : Day {
    private val regex = Regex("([a-z]{1,5}) (inc|dec) (-?[0-9]+) if ([a-z]{1,5}) (>|<|>=|<=|==|!=) (-?[0-9]+)")

    private val tests: Map<String, (Int, Int) -> Boolean> = mapOf(
            "==" to { a, b -> a == b },
            "!=" to { a, b -> a != b },
            ">" to { a, b -> a > b },
            "<" to { a, b -> a < b },
            ">=" to { a, b -> a >= b },
            "<=" to { a, b -> a <= b }
    )

    private val registers: Map<String, Int> by lazy { Day08.solve(Day08.parse(resourceRegex(8, regex))) }

    private fun solve(instructions: List<Instruction>): Map<String, Int> {
        val registers: MutableMap<String, Int> = mutableMapOf()
        registers["_max"] = 0

        instructions.filter { it.eq(registers.computeIfAbsent(it.testReg, { 0 }), it.testVal) }.forEach {
            val regVal = registers.computeIfAbsent(it.reg, { 0 })

            registers[it.reg] = it.op(regVal, it.amount)

            registers["_max"] = Math.max(registers["_max"]!!, registers[it.reg]!!)
        }

        return registers
    }

    private fun parse(lines: List<List<String>>) = lines
            .map { Instruction(it[1], if (it[2] == "inc") { a, b -> a + b } else { a, b -> a - b }, it[3].toInt(), it[4], tests[it[5]]!!, it[6].toInt()) }

    override fun part1() = registers.entries.filter { !it.key.startsWith("_") }.map { it.value }.max().toString()
    override fun part2() = registers["_max"]!!.toString()

    data class Instruction(val reg: String, val op: (a: Int, b: Int) -> Int, val amount: Int, val testReg: String, val eq: (Int, Int) -> Boolean, val testVal: Int)
}