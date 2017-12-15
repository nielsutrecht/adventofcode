package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.Maze

interface MazePath {
    fun search(maze: Maze, from: Point, to: Point) = search(maze, from, to, Point::neighborsHv)
    fun search(maze: Maze, from: Point, to: Point, neighbors: (Point) -> List<Point>) : List<Point>
}