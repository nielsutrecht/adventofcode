package com.nibado.projects.advent.collect

import com.nibado.projects.advent.Point
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MazeTest {
    lateinit var maze: Maze
    @BeforeEach
    fun setup() {
        maze = Maze(10, 10)
    }

    @Test
    fun dfs() {
        (0 .. 4).forEach {
            maze.set(it, 1, true)
        }

        val result = maze.dfs(Point(0, 0), Point(5, 5))

        result.forEach { println(it) }

        maze.print(result.toSet())
    }

    @Test
    fun aStar() {

    }
}