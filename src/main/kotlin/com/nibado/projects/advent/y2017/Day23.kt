package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day23 : Day {
    private val input = resourceLines(23).map { it.split(" ") }.map { listOf(it[0], it[1], if(it.size == 3) it[2] else "") }
    override fun part1() :String {
        val registers = mutableMapOf<String, Long>()

        fun value(s: String) = if(s[0].isLetter()) registers.computeIfAbsent(s, {0L}) else s.toLong()

        var index = 0
        var count = 0
        while(index < input.size) {
            val (op, reg1, reg2) = input[index]
            when(op) {
                "set" -> { registers[reg1] = value(reg2)}
                "mul" -> {
                    registers[reg1] = value(reg1) * value(reg2)
                    count++
                }
                "sub" -> { registers[reg1] = value(reg1) - value(reg2)}
                "jnz" -> {
                    if(value(reg1) != 0L) {
                        index += value(reg2).toInt() - 1
                    }
                }
            }

            index++
        }

        return count.toString()
    }

    override fun part2() :String {
        var b = 0
        var c = 0
        var d = 0
        var e = 0
        var f = 0
        var g = 0
        var h = 0
        b = 109900

        c = b
        c -= -17000

        do {
            f = 1
            d = 2
            while (d < b) {
                if (b % d == 0) {
                    f = 0
                    break
                }
                d++
            }
            if (f == 0) {
                h++
            }
            g = b - c
            b += 17
        } while (g != 0)
        return h.toString()
    }
}