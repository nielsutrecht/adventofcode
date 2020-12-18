package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day17 : Day {
    private val grid = resourceLines(2020, 17)
            .mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c == '#') listOf(x, y, 0) else null } }
            .flatten().toSet()

    override fun part1() = solve(grid)
    override fun part2() = solve(grid.map { it + 0 }.toSet())

    private fun solve(input: Set<List<Int>>): Any {
        var points = input
        val neighbors = neighbors(points.first().size)

        fun neighbors(list: List<Int>) = neighbors.map { n -> list.mapIndexed { i, v -> n[i] + v } }

        repeat(6) {
            val map = points.flatMap { neighbors(it) }.fold(mutableMapOf<List<Int>, Int>()) { map, p ->
                map[p] = map.getOrDefault(p, 0) + 1
                map
            }

            points = map.filter { (p, c) -> (p in points && c in 2..3) || (p !in points && c == 3) }
                    .map { it.key }.toSet()
        }

        return points.size
    }

    private fun neighbors(dim: Int) = (1 until dim).fold((-1..1).map { listOf(it) }) { points, _ ->
        points.flatMap { p -> (-1..1).map { p + it } }
    }.filterNot { it.all { it == 0 } }
}
