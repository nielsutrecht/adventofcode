package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day17 : Day {
    private val grid = resourceLines(2020, 17)
            .mapIndexed { y, s -> s.mapIndexedNotNull { x, c -> if (c == '#') listOf(x, y, 0) else null } }
            .flatten().toSet()

    override fun part1() = solve(grid, Day17::neighbors3)
    override fun part2() = solve(grid.map { (x, y, z) -> listOf(x, y, z, 0) }.toSet(), Day17::neighbors4)

    private fun solve(input: Set<List<Int>>, neighbors: (List<Int>) -> List<List<Int>>): Any {
        var points = input

        for (i in 1..6) {
            val map = points.flatMap { neighbors(it) }.fold(mutableMapOf<List<Int>, Int>()) { map, p ->
                map[p] = map.getOrDefault(p, 0) + 1
                map
            }

            points = map.filter { (p, c) ->
                (points.contains(p) && c in 2..3) || (!points.contains(p) && c == 3)
            }.map { it.key }.toSet()
        }

        return points.size
    }

    private fun neighbors3(point: List<Int>) =  (-1 .. 1).flatMap { x -> (-1 .. 1).flatMap { y -> (-1 .. 1)
            .map { z -> listOf(x,y,z) }}}
            .filterNot { (a,b,c) -> a == 0 && b == 0 && c == 0 }
            .map { (a,b,c) -> point.let { (x,y,z) -> listOf(a + x, b + y, c + z) } }

    private fun neighbors4(point: List<Int>) =
            (-1 .. 1).flatMap { x -> (-1 .. 1).flatMap { y -> (-1 .. 1).flatMap { z -> (-1 .. 1).map { listOf(x,y,z,it) } } } }
                    .filterNot { (a,b,c,d) -> a == 0 && b == 0 && c == 0 && d == 0 }
                    .map { (a,b,c,d) -> point.let { (x,y,z,zz) -> listOf(a + x, b + y, c + z, d + zz) } }
}
