package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.Maze

object BreadthFirst : MazePath {
    override fun search(maze: Maze, from: Point, to: Point, neighbors: (Point) -> List<Point>): List<Point> {
        val frontier = mutableSetOf<Point>()
        val cameFrom = mutableMapOf<Point, Point>()

        frontier += from

        while(frontier.isNotEmpty()) {
            val current = frontier.firstOrNull()!!
            frontier -= current

            val next = neighbors(current)
                    .filter { maze.inBound(it) }
                    .filterNot { cameFrom.containsKey(it) }
                    .filterNot { maze.isWall(it) }

            frontier += next
            next.forEach { cameFrom[it] = current }

            if(cameFrom.containsKey(to)) {
                break
            }
        }

        if(!cameFrom.containsKey(to)) {
            return listOf()
        }

        var current = to
        val path = mutableListOf(to)

        while(current != from) {
            current = cameFrom[current]!!

            path += current
        }

        path.reverse()

        return path
    }
}
