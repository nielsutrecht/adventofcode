package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.Maze

object AStar : MazePath {
    override fun search(maze: Maze, from: Point, to: Point, neighbors: (Point) -> List<Point>): List<Point> {
        return neighbors(from)
    }
}