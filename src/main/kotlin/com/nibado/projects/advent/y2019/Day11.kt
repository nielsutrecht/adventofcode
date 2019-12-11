package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.*
import java.util.concurrent.LinkedBlockingQueue

object Day11 : Day {
    private val program = resourceString(2019, 11).split(",").map { it.toLong() }

    override fun part1() = solve(0).first
    override fun part2() = solve(1).second

    private fun solve(start: Int) : Pair<Int, String> {
        val input = LinkedBlockingQueue<Long>()
        val output = LinkedBlockingQueue<Long>()

        val brain = IntCode(program, input, output)

        val grid = mutableMapOf<Point, Int>()
        var direction = Direction.NORTH
        var currentPoint = Point(0,0)
        input.put(start.toLong())

        while(!brain.terminated) {
            while (!brain.terminated && brain.output.size < 2) {
                brain.step()
            }

            if(!brain.terminated) {
                grid[currentPoint] = output.take().toInt()
                direction = if (output.take() == 0L) direction.ccw() else direction.cw()
                currentPoint += direction

                val color = grid.getOrDefault(currentPoint, 0).toLong()
                input.put(color)
            }
        }

        return grid.keys.size to render(grid)
    }

    private fun render(grid: Map<Point, Int>) : String {
        val bounds = (grid.keys ).bounds()
        return bounds.points().map { it to grid.getOrDefault(it, -1) }.map {
            when (it.second) {
                1 -> '#'
                else -> ' '
            }
        }.chunked(bounds.second.x - bounds.first.x + 1)
            .map { it.joinToString("").trimEnd() }
            .joinToString("\n")
    }
}