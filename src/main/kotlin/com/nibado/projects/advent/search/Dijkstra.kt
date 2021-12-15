package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.NumberGrid
import com.nibado.projects.advent.graph.*

object Dijkstra : GraphShortestPath {
    override fun <N, E : Number> search(graph: Graph<N, E>, from: N, to: N): List<N> {
        TODO("Unfinished")

        return emptyList()
    }

    fun <N: Number> search(grid: NumberGrid<N>, from: Point, to: Point) : List<Point> {
        val unvisitedSet = grid.points.toMutableSet()
        val distances = grid.points.map { it to Double.POSITIVE_INFINITY }.toMap().toMutableMap()
        val adjacency = mutableMapOf<Point, Point>()
        distances[from] = 0.0

        val frontier = mutableSetOf<Point>()
        var current = from

        while (unvisitedSet.isNotEmpty() && unvisitedSet.contains(to)) {
            val neighbors = current.neighborsHv().filter { grid.inBound(it) && unvisitedSet.contains(it) }
            neighbors.forEach { adjacent ->
                val distance = grid[adjacent].toDouble()
                if (distances[current]!! + distance < distances[adjacent]!!) {
                    distances[adjacent] = distances[current]!! + distance
                    adjacency[adjacent] = current
                }
            }
            frontier += neighbors

            unvisitedSet -= current
            frontier -= current

            if (current == to) {
                break
            }

            if (frontier.isNotEmpty()) {
                current = frontier.minByOrNull { distances[it]!! }!!
            }
        }

        val path = mutableListOf(to)
        while(adjacency.containsKey(path.last())) {
            path += adjacency[path.last()]!!
        }

        return path
    }
}