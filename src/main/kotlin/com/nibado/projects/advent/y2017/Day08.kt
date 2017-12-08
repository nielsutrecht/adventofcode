package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day08 : Day {
    val regex = Regex("([a-z]{1,5}) (inc|dec) (-?[0-9]+) if ([a-z]{1,5}) (>|<|>=|<=|==|!=) (-?[0-9]+)")

    val registers: Map<String, Int> by lazy { Day08.solve(Day08.parse(resourceLines(8))) }

    fun solve(instructions: List<Instruction>): Map<String, Int> {
        val registers: MutableMap<String, Int> = mutableMapOf()
        registers["_max"] = 0

        instructions.filter { test(registers, it.testReg, it.eq, it.testVal) }.forEach {
            val regVal = registers.computeIfAbsent(it.reg, { 0 })
            when (it.incDec) {
                "dec" -> registers[it.reg] = regVal - it.amount
                "inc" -> registers[it.reg] = regVal + it.amount
            }
            registers["_max"] = Math.max(registers["_max"]!!, registers[it.reg]!!)
        }

        return registers
    }

    fun test(registers: MutableMap<String, Int>, reg: String, eq: String, testVal: Int): Boolean {
        val regVal = registers.computeIfAbsent(reg, { 0 })

        return when (eq) {
            "==" -> regVal == testVal
            "!=" -> regVal != testVal
            ">" -> regVal > testVal
            "<" -> regVal < testVal
            ">=" -> regVal >= testVal
            "<=" -> regVal <= testVal
            else -> {
                false
            }
        }
    }

    fun parse(lines: List<String>) = lines
            .map { regex.matchEntire(it) }
            .map { it!!.groupValues }
            .map { Instruction(it[1], it[2], it[3].toInt(), it[4], it[5], it[6].toInt()) }

    override fun part1() = registers.entries.filter { !it.key.startsWith("_") }.map { it.value }.max().toString()
    override fun part2() = registers["_max"]!!.toString()

    data class Instruction(val reg: String, val incDec: String, val amount: Int, val testReg: String, val eq: String, val testVal: Int)
}