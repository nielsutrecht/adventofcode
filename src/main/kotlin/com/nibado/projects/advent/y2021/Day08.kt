package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day08 : Day {
    val values = resourceLines(2021, 8).map {
        it.split("\\s*\\|\\s*".toRegex())
                .let { (pattern, digits) -> pattern.split(' ') to digits.split(' ').map { it.trim() } }
    }

    override fun part1() = values.flatMap { it.second }.count { it.length in UNIQUE_LENGTHS }

    override fun part2() = values.sumOf { (pattern, display) -> mapDisplay(display, createMapping(pattern)) }

    private fun createMapping(pattern: List<String>): Map<Char, Int> {
        val patternSets = pattern.map { it.toCharArray().toSet() }
        val n1 = patternSets.single { it.size == 2 }
        val n7 = patternSets.single { it.size == 3 }
        val n4 = patternSets.single { it.size == 4 }
        val n8 = patternSets.single { it.size == 7 }

        var wire = CharArray(7)

        wire[0] = (n7 - n1).single() //OK

        val n3 = patternSets.single { it.size == 5 && it.containsAll(n1) } //OK

        wire[6] = (n3 - n4 - wire[0]).single()
        wire[4] = (n8 - n4 - n7 - wire[6]).single()

        val n2 = patternSets.single { it.size == 5 && it != n3 && it.contains(wire[4]) }
        val n5 = patternSets.single { it.size == 5 && it != n3 && it != n2 }

        wire[1] = (n8 - n3 - n2).single()
        wire[2] = (n1 - n5).single()
        wire[5] = (n1 - wire[2]).single()
        wire[3] = (n4 - setOf(wire[1], wire[2], wire[5])).single()

        return wire.mapIndexed { index, c -> c to index }.toMap()
    }

    private fun mapDisplay(display: List<String>, mapping: Map<Char, Int>): Int {
        val toSegments = display.map { d ->
            d.map { c ->
                mapping[c]!!
            }.toSet()
        }
        val toInts = toSegments.map { segment -> DIGITS.indexOfFirst { it == segment } }

        return toInts.joinToString("").toInt()
    }

    private val DIGITS = listOf(
            setOf(0, 1, 2, 4, 5, 6),
            setOf(2, 5),
            setOf(0, 2, 3, 4, 6),
            setOf(0, 2, 3, 5, 6),
            setOf(1, 2, 3, 5),
            setOf(0, 1, 3, 5, 6),
            setOf(0, 1, 3, 4, 5, 6),
            setOf(0, 2, 5),
            setOf(0, 1, 2, 3, 4, 5, 6),
            setOf(0, 1, 2, 3, 5, 6)
    )
    private val UNIQUE = listOf(1, 4, 7, 8)
    private val UNIQUE_LENGTHS = UNIQUE.map { DIGITS[it].size }
}