package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.Maze
import com.nibado.projects.advent.search.BreadthFirst

object Day13 : Day {
    private val input = 1362
    private val solution : Map<Point, Int> by lazy { solve(40,40) }

    private fun solve(width: Int, height: Int) : Map<Point, Int> {
        val maze = Maze(width, height)

        for(y in 0 until height) {
            for(x in 0 until width) {
                val hash = x * x + 3 * x + 2 * x * y + y + y * y + input
                val wall = hash.toString(2).toCharArray().count { it == '1' } % 2 == 1

                maze.set(x, y, wall)
            }
        }

        return (0 .. 39).flatMap { x -> ( 0 .. 39).map { y -> Point(x, y) } }
                .filter { maze.inBound(it) }
                .filterNot { maze.isWall(it) }
                .map { it to BreadthFirst.search(maze, Point(1, 1), it).toSet().size - 1 }
                .filter { it.second >= 0 }.toMap()
    }

    override fun part1() = solution[Point(31,39)]!!.toString()
    override fun part2() = solution.values.filter { it <= 50 }.count().toString()
}