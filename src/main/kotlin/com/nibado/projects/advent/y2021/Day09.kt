package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import java.util.*

object Day09 : Day {
    private val values = resourceLines(2021, 9).map { it.toCharArray().map { it.digitToInt() } }
    private val points = values.indices.flatMap { y -> values[0].indices.map { x -> Point(x, y) } }

    override fun part1() = points.map { it to values[it.y][it.x] }.filter {
        val neighbors = it.first.neighborsHv().filter { it.inBound(values[0].size - 1, values.size - 1) }
        neighbors.all { n -> values[n.y][n.x] > it.second }
    }.sumOf { it.second + 1 }

    override fun part2() : Int {
        val consider = points.filterNot { (x, y) -> values[y][x] == 9 }.toMutableSet()
        val sizes = TreeSet<Int>()

        while(consider.isNotEmpty()) {
            var area = 0
            var frontier = setOf(consider.first())

            while(frontier.isNotEmpty()) {
                area += frontier.size
                consider -= frontier
                frontier = frontier.flatMap { it.neighborsHv() }.filter { consider.contains(it) }.toSet()
            }
            sizes += area
        }

        return sizes.toList().takeLast(3).let { (a,b,c) -> a * b * c }
    }
}