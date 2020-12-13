package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day10 : Day {
    private val adapters = resourceLines(2020, 10).map { it.toInt() }.sorted()
    private val chain = listOf(0) + adapters + (adapters.last() + 3)

    override fun part1(): Int = chain.windowed(2).map { (a, b) -> b - a }
            .groupBy { it }.map { (k, v) -> v.size }.let { (a, b) -> a * b }

    override fun part2(): Long {
        val input = chain.toMutableList()
        val subChains = mutableListOf<List<Int>>()

        var current = mutableListOf<Int>()

        while (input.isNotEmpty()) {
            do {
                current.add(input.removeAt(0))
                if (input.isEmpty() || input.first() - current.last() >= 3) {
                    subChains += current.toList()
                    current = mutableListOf()
                    break
                }
            } while (true)
        }

        return subChains.map { when(it.size) {
            3 -> 2
            4 -> 4
            5 -> 7
            else -> 1
        }.toLong() }.reduce { a, b -> a * b }
    }
}
