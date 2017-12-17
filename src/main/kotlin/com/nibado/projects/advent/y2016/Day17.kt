package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Hash
import com.nibado.projects.advent.Point

object Day17 : Day {
    private val dirMap = mapOf("D" to Direction.SOUTH, "U" to Direction.NORTH, "R" to Direction.EAST, "L" to Direction.WEST)
    private val input = "hhhxzeay"
    private val solution : Pair<String, String> by lazy { solve() }

    override fun part1() = solution.first
    override fun part2() = solution.second.length.toString()

    private fun solve() : Pair<String, String> {
        val solutions = mutableListOf<String>()

        search("", Point(0, 0), solutions)

        return solutions.minBy { it.length }!! to solutions.maxBy { it.length }!!
    }

    private fun search(path: String, current: Point, solutions: MutableList<String>) {
        if(current == Point(3, 3)) {
            solutions += path
            return
        }

        val md5 = Hash.md5(input + path)
        val next = directions(md5)
                .map { it.first to current.add(it.second) }
                .filter { it.second.inBound(3,3) }

        next.forEach {
            search(path + it.first, it.second, solutions)
        }
    }

    private fun directions(md5: String) = listOf("U", "D", "L", "R").filterIndexed { i, _ -> md5[i] in 'b' .. 'f'}.map { it to dirMap[it]!! }
}