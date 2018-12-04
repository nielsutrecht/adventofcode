package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.Maze
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BreadthFirstTest {
    companion object {
        fun maze() : Maze {
            val maze = Maze(10, 8)

            maze.set(0, 1, true)
            maze.set(1, 1, true)
            maze.set(2, 1, true)
            maze.set(3, 1, true)
            maze.set(4, 1, true)

            return maze
        }
    }
    lateinit var maze: Maze;

    @BeforeEach
    fun setup() {
        maze = maze()
    }

    @Test
    fun search() {
        var path = BreadthFirst.search(maze, Point(0, 0), Point(1, 3))

        assertThat(path).hasSize(13)

        path = BreadthFirst.search(maze, Point(0, 0), Point(9, 7))
        assertThat(path).hasSize(17)

        path = BreadthFirst.search(maze, Point(0, 0), Point(10, 10))
        assertThat(path).isEmpty()
    }
}