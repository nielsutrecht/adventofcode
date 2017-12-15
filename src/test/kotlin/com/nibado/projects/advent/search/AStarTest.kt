package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.Maze
import org.junit.Before
import org.junit.Test

class AStarTest {
    lateinit var maze: Maze

    @Before
    fun setup() {
        maze = BreadthFirstTest.maze()
    }

    @Test
    fun search() {
        val path = AStar.search(maze, Point(0, 0), Point(1, 3))
        maze.print(path.toSet())
    }

}