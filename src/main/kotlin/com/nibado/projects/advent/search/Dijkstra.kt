package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.NumberGrid
import com.nibado.projects.advent.graph.Graph

object Dijkstra : GraphShortestPath, GridShortestPath {
    override fun <N, E : Number> shortestPath(graph: Graph<N, E>, from: N, to: N): List<N> {
        val distances = graph.nodes().associateWith { Double.POSITIVE_INFINITY }.toMutableMap()
        val unvisitedSet = graph.nodes().toMutableSet()
        val adjacency = mutableMapOf<N, N>()
        distances[from] = 0.0
        var current = from
        val frontier = mutableSetOf<N>()

        while(unvisitedSet.isNotEmpty() && unvisitedSet.contains(to)) {
            val neighbors = graph.edges(current).filter { unvisitedSet.contains(it.to.key) }
            neighbors.forEach { (d, node) ->
                val distance = d.toDouble()
                val next = node.key
                if (distances[current]!! + distance < distances[next]!!) {
                    distances[next] = distances[current]!! + distance
                    adjacency[next] = current
                }
                frontier += next
            }

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

        return path.reversed()
    }

    override fun <N: Number> shortestPath(grid: NumberGrid<N>, from: Point, to: Point) : List<Point> {
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