package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day19 : Day {
    private val input = resourceLines(2018, 19)
    private val instructions = input.drop(1).map { it.split(" ") }.map { Day16.opCodes[it[0]]!! to it.drop(1).map { it.toInt() } }
    private val ip = input[0].removePrefix("#ip ").toInt()

    private fun run(registers: MutableList<Int>, breakOn: Int? = null) : List<Int> {
        while(registers[ip] < instructions.size) {
            val inst = instructions[registers[ip]]
            val (a, b, c) = inst.second

            inst.first(a,b,c,registers)

            if(breakOn != null && breakOn == registers[ip]) {
                break
            }

            registers[ip]++
        }

        return registers
    }
    private fun sumOfFactors(n: Int)= (1 .. n).filter { n % it == 0 }.sum()

    override fun part1() = run(mutableListOf(0, 0, 0, 0, 0, 0)).first()
    override fun part2() = sumOfFactors(run(mutableListOf(1, 0, 0, 0, 0, 0), 10)[4])
}