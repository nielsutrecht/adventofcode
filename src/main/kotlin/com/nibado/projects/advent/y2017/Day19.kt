package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Direction
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.resourceLines

object Day19 : Day {
    private val maze = resourceLines(2017, 19).map { it.toCharArray().toList() }
    private val solution: Pair<String, Int> by lazy { solve() }

    override fun part1() = solution.first
    override fun part2() = solution.second.toString()

    private fun solve(): Pair<String, Int> {
        var count = 0
        var current = findStart()
        var dir = Direction.SOUTH
        var chars = mutableListOf<Char>()
        var visited = mutableSetOf<Point>()

        while (get(current) != ' ') {
            count++
            visited.add(current)
            if (get(current) in 'A'..'Z') {
                chars.add(get(current))
            }

            if (get(current) == '+') {
                val next = current.neighborsHv()
                        .filterNot { get(it) == ' ' }
                        .filterNot { visited.contains(it) }
                        .first()

                dir = current.directionTo(next)
                current = next
            } else {
                current = current.add(dir)
            }
        }

        return Pair(chars.joinToString(""), count)
    }

    private fun get(p: Point) = if (p.x >= 0 && p.y >= 0 && maze.size > p.y && maze[p.y].size > p.x) maze[p.y][p.x] else ' '
    private fun findStart() = (0 until maze[0].size).map { Point(it, 0) }.find { get(it) == '|' }!!
}