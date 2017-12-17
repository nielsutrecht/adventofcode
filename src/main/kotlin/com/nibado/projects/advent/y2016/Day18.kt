package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.Maze

object Day18 : Day {
    private val input = "^..^^.^^^..^^.^...^^^^^....^.^..^^^.^.^.^^...^.^.^.^.^^.....^.^^.^.^.^.^.^.^^..^^^^^...^.....^....^."

    override fun part1() = solve(40)
    override fun part2() = solve(400000)

    private fun solve(height: Int): String {
        val maze = Maze(input.length, height)

        fun trap(point: Point) = if (!maze.inBound(point)) { false } else { maze.isWall(point) }
        fun left(point: Point) = trap(Point(point.x - 1, point.y - 1))
        fun center(point: Point) = trap(Point(point.x, point.y - 1))
        fun right(point: Point) = trap(Point(point.x + 1, point.y - 1))

        input.forEachIndexed { i, c -> maze.set(i, 0, c == '^') }

        (1 until maze.height).forEach { y ->
            (0 until maze.width).forEach { x ->
                val it = Point(x, y)

                if (left(it) && center(it) && !right(it) ||
                        right(it) && center(it) && !left(it) ||
                        left(it) && !center(it) && !right(it) ||
                        right(it) && !center(it) && !left(it)) {

                    maze.set(it.x, it.y, true)
                }
            }
        }

        return maze.count().toString()
    }
}