package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day24 : Day {
    private val input = resourceLines(2017, 24).map { it.split("/").map { it.toInt() } }.map { it[0] to it[1] }.sortedBy { it.first }
    private val solution: List<List<Pair<Int, Int>>> by lazy { solve() }

    override fun part1() = solution.last().map { it.first + it.second }.sum().toString()
    override fun part2(): String {
        val maxLength = solution.maxBy { it.size }!!.size

        return solution.last { it.size == maxLength }.map { it.first + it.second }.sum().toString()
    }

    private fun solve(): List<List<Pair<Int, Int>>> {
        val solutions = mutableListOf<List<Pair<Int, Int>>>()

        search(listOf(), 0, input.toSet(), solutions)

        return solutions.map { it to it.map { it.first + it.second }.sum() }.filter { it.second > 1000 }.sortedBy { it.second }.map { it.first }
    }

    private fun search(current: List<Pair<Int, Int>>, port: Int, available: Set<Pair<Int, Int>>, solutions: MutableList<List<Pair<Int, Int>>>) {
        solutions.add(current)

        available.filter { it.first == port }.forEach {
            search(current + it, it.second, available.minus(it), solutions)
        }
        available.filter { it.second == port }.forEach {
            search(current + it, it.first, available.minus(it), solutions)
        }
    }
}