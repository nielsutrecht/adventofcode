package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.Maze

object Day13 : Day {
    private val input = 10
    val maze = generate(10,7)

    fun generate(width: Int, height: Int) : Maze {
        val maze = Maze(width, height)

        for(y in 0 until height) {
            for(x in 0 until width) {
                val hash = x * x + 3 * x + 2 * x * y + y + y * y + input
                val wall = hash.toString(2).toCharArray().count { it == '1' } % 2 == 1

                maze.set(x, y, wall)
            }
        }

        return maze
    }

    override fun part1() = ""
    override fun part2() = ""

}

fun main(args: Array<String>) {
    Day13.maze.print(setOf(Point(1, 1), Point(1, 2), Point(2, 2), Point(3, 2)))
}