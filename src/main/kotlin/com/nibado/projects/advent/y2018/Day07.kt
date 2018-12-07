package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex

object Day07 : Day {
    private val regex = "Step ([A-Z]) must be finished before step ([A-Z]) can begin\\.".toRegex()
    private val input = resourceRegex(2018, 7, regex).map { it.drop(1) }.map { (a, b) -> a.first() to b.first() }
    private val characters = input.flatMap { listOf(it.first, it.second) }.toSet().sorted().toList()
    private val prereqs : Map<Char, Set<Char>> by lazy {
        val prereqs = mutableMapOf<Char, MutableSet<Char>>()

        input.forEach {
            prereqs.computeIfAbsent(it.second) { mutableSetOf() }.add(it.first)
        }

        prereqs
    }

    override fun part1(): String {
        val completed = mutableSetOf<Char>()
        val result = mutableListOf<Char>()

        while (completed.size < characters.size) {
            val canComplete = characters.filterNot { completed.contains(it) }
                    .filter { a -> !prereqs.containsKey(a) || prereqs[a]!!.all { b -> completed.contains(b) } }

            val c = canComplete.first()

            completed += c
            result += c
        }

        return result.joinToString("")
    }

    override fun part2(): Int {
        val completed = mutableSetOf<Char>()
        val result = mutableListOf<Char>()

        var workers = 0
        var second = 0
        var until = mutableMapOf<Char, Int>()

        while (completed.size < characters.size) {
            val done = until.filter { it.value == second }.keys.sorted()

            done.forEach { until.remove(it) }

            workers -= done.size

            completed += done
            result += done

            val canComplete = characters.filterNot { completed.contains(it) || until.keys.contains(it) }
                    .filter { a -> !prereqs.containsKey(a) || prereqs[a]!!.all { b -> completed.contains(b) } }
                    .sorted()
                    .take(5 - workers)

            workers += canComplete.size
            canComplete.forEach { until[it] = second + (it - 'A' + 61) }

            second++
        }

        return second - 1
    }
}