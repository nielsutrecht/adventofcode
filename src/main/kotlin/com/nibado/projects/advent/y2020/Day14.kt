package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day14 : Day {
    private interface Day14Input
    private data class Mask(val mask: String) : Day14Input
    private data class Mem(val index: Int, val value: Int) : Day14Input

    private val maskRegex = "mask = ([01X]{36})".toRegex()
    private val memRegex = "mem.([0-9]+). = ([0-9]+)".toRegex()
    private val values = resourceLines(2020, 14).map(::parse)

    private fun parse(line: String): Day14Input =
            when {
                maskRegex.matches(line) -> maskRegex.matchEntire(line)!!.groupValues.let { (_, a) -> Mask(a) }
                memRegex.matches(line) -> memRegex.matchEntire(line)!!.groupValues.let { (_, a, b) -> Mem(a.toInt(), b.toInt()) }
                else -> throw IllegalArgumentException(line)
            }

    override fun part1(): Long {
        var mask = ""
        val registers = mutableMapOf<Int, Long>()
        for (inst in values) {
            when (inst) {
                is Mask -> mask = inst.mask
                is Mem -> registers[inst.index] = mask(mask, inst.value)
            }
        }

        return registers.values.sum()
    }

    override fun part2() : Long {
        var mask = ""
        val registers = mutableMapOf<Long, Long>()

        for (inst in values) {
            when (inst) {
                is Mask -> mask = inst.mask
                is Mem -> addresses(mask, inst.index).forEach { registers[it] = inst.value.toLong() }
            }
        }

        return registers.values.sum()
    }

    private fun mask(mask: String, value: Int): Long = value
            .toString(2).padStart(36, '0')
            .zip(mask) { a, b -> if (b == 'X') a else b }.joinToString("").toLong(2)

    private fun addresses(mask: String, address: Int) : List<Long> {
        val initial = address.toString(2).padStart(36, '0')
                .zip(mask) { a, b -> if (b == '0') a else b }.joinToString("")

        val bits = initial.count { it == 'X' }
        val max = "1".repeat(bits).toInt(2)

        val list = (0 .. max).map { it.toString(2).padStart(bits, '0').toMutableList() }.map { chars ->
            initial.map { if(it == 'X') chars.removeAt(0) else it }.joinToString("")
        }

        return list.map { it.toLong(2) }
    }
}
