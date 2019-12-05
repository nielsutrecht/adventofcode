package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString
import com.nibado.projects.advent.y2019.Day05.IntCode.Opcode.*

object Day05 : Day {
    private val input = resourceString(2019, 5).split(",").map { it.toInt() }

    override fun part1() = IntCode(input.toMutableList(), mutableListOf(1)).also { it.run() }.output.last()
    override fun part2() = IntCode(input.toMutableList(), mutableListOf(5)).also { it.run() }.output.last()

    class IntCode(
        val memory: MutableList<Int>,
        val input: MutableList<Int> = mutableListOf(),
        val output: MutableList<Int> = mutableListOf()
    ) {
        fun run() {
            var ip = 0
            while (true) {
                val op = values().find { it.op == memory[ip] % 100 } ?: throw IllegalArgumentException()
                val instruction = Instruction(op,
                    (1 until op.len).map { memory[ip + it] },
                    (memory[ip] / 100).toString().padStart(op.len - 1, '0').map { it == '1' }.reversed()
                )
                ip = instruction.apply(ip, memory, input, output) ?: return
            }
        }

        data class Instruction(val op: Opcode, val params: List<Int>, val modes: List<Boolean>) {
            fun apply(ip: Int, memory: MutableList<Int>, input: MutableList<Int>, output: MutableList<Int>): Int? {
                fun get(param: Int) = if (modes[param]) params[param] else memory[params[param]]

                when (op) {
                    ADD     -> memory[params[2]] = get(0) + get(1)
                    MUL     -> memory[params[2]] = get(0) * get(1)
                    SAV     -> memory[params[0]] = input.removeAt(0)
                    OUT     -> output.add(get(0))
                    JIT     -> if (get(0) != 0) return get(1)
                    JIF     -> if (get(0) == 0) return get(1)
                    LT      -> memory[params[2]] = if(get(0) < get(1)) 1 else 0
                    EQ      -> memory[params[2]] = if(get(0) == get(1)) 1 else 0
                    TERM    -> return null
                }

                return ip + op.len
            }
        }

        enum class Opcode(val op: Int, val len: Int) {
            ADD(1,4),
            MUL(2,4),
            SAV(3,2),
            OUT(4,2),
            JIT(5,3),
            JIF(6,3),
            LT(7,4),
            EQ(8,4),
            TERM(99,1)
        }
    }
}