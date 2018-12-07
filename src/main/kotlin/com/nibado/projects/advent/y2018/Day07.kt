package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex

object Day07 : Day {
    private val regex = "Step ([A-Z]) must be finished before step ([A-Z]) can begin\\.".toRegex()
    private val input = resourceRegex(2018, 7, regex).map { it.drop(1) }.map { (a, b) -> a.first() to b.first() }
    private val characters = input.flatMap { listOf(it.first, it.second) }.toSet().sorted().toList()
    private val preReqs = input.groupBy { it.second }.map { it.key to it.value.map { it.first }.toSet() }.toMap()

    override fun part1(): String {
        val result = mutableListOf<Char>()

        while (result.size < characters.size) {
            result += characters
                    .filterNot { result.contains(it) }
                    .first { a -> !preReqs.containsKey(a) || preReqs[a]!!.all { b -> result.contains(b) } }
        }

        return result.joinToString("")
    }

    override fun part2(): Int {
        val result = mutableListOf<Char>()

        var workers = 0
        var second = 0
        var until = mutableMapOf<Char, Int>()

        while (result.size < characters.size) {
            with(until.filter { it.value == second }.keys.sorted()) {
                forEach { until.remove(it) }
                workers -= size
                result += this
            }

            characters.filterNot { result.contains(it) || until.keys.contains(it) }
                    .filter { a -> !preReqs.containsKey(a) || preReqs[a]!!.all { b -> result.contains(b) } }
                    .sorted()
                    .take(5 - workers)
                    .also { workers += it.size }
                    .forEach { until[it] = second + (it - 'A' + 61) }

            second++
        }

        return second - 1
    }
}