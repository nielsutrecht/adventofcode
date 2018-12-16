package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day16 : Day {
    private val samples = resourceLines(2018, 16).windowed(4, 4)
            .takeWhile { it[0].startsWith("Before") }
            .map(::parse)

    private val program = resourceLines(2018, 16).windowed(3, step = 1, partialWindows = true)
            .dropWhile { it.any { !it.matches("[0-9 ]+".toRegex()) } }
            .map { it.first().split(" ").map { it.toInt() } }

    private val opCodes = mapOf(
            "addr" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = reg[a] + reg[b] },
            "addi" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = reg[a] + b },
            "mulr" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = reg[a] * reg[b] },
            "muli" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = reg[a] * b },
            "banr" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = reg[a] and reg[b] },
            "bani" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = reg[a] and b },
            "borr" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = reg[a] or reg[b] },
            "bori" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = reg[a] or b },
            "setr" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = reg[a] },
            "seti" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = a },
            "gtir" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = if (a > reg[b]) 1 else 0 },
            "gtri" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = if (reg[a] > b) 1 else 0 },
            "gtrr" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = if (reg[a] > reg[b]) 1 else 0 },
            "eqir" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = if (a == reg[b]) 1 else 0 },
            "eqri" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = if (reg[a] == b) 1 else 0 },
            "eqrr" to { a: Int, b: Int, c: Int, reg: MutableList<Int> -> reg[c] = if (reg[a] == reg[b]) 1 else 0 }
    )

    private fun parse(sample: List<String>): Sample {
        val before = sample[0].let { it.removePrefix("Before: [").removeSuffix("]").split(", ").map { it.toInt() } }
        val opcode = sample[1].let { it.split(" ").map { it.toInt() } }
        val after = sample[2].let { it.removePrefix("After:  [").removeSuffix("]").split(", ").map { it.toInt() } }

        return Sample(before, opcode, after)
    }

    private fun match(sample: Sample, f: (Int, Int, Int, MutableList<Int>) -> Unit): Boolean {
        val reg = sample.before.toMutableList()
        f(sample.a, sample.b, sample.c, reg)

        return reg == sample.after
    }

    private fun find(sample: Sample) = opCodes.filter { match(sample, it.value) }

    data class Sample(val before: List<Int>, val instruction: List<Int>, val after: List<Int>) {
        val op = instruction[0]
        val a = instruction[1]
        val b = instruction[2]
        val c = instruction[3]
    }

    override fun part1() = samples.map { find(it) }.count { it.size >= 3 }

    override fun part2(): Int {
        val opNumberMap = mutableMapOf<Int, String>()
        do {
            val result = samples
                    .filterNot { opNumberMap.keys.contains(it.op) }
                    .map { it to find(it).filterNot { opNumberMap.values.contains(it.key) } }
                    .filter { it.second.size == 1 }
                    .map { it.first.op to it.second.keys.first() }

            opNumberMap.putAll(result)

        } while (samples.any { !opNumberMap.keys.contains(it.op) })

        val register = mutableListOf(0, 0, 0, 0)

        program.map { opNumberMap[it.first()] to it.drop(1) }
                .filterNot { it.first == null }
                .map { opCodes[it.first]!! to it.second }
                .forEach { it.first(it.second[0], it.second[1], it.second[2], register) }

        return register.first()
    }
}