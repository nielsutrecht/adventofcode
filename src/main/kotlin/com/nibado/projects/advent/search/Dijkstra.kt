package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.NumberGrid
import com.nibado.projects.advent.graph.Graph
import java.util.*

object Dijkstra : GraphShortestPath, GridShortestPath {
    override fun <N, E : Number> shortestPath(graph: Graph<N, E>, from: N, to: N): List<N> {
        val distances = graph.nodes().associateWith { Double.POSITIVE_INFINITY }.toMutableMap()
        val unvisitedSet = graph.nodes().toMutableSet()
        val adjacency = mutableMapOf<N, N>()
        distances[from] = 0.0
        var current = from
        val frontier = PriorityQueue<Pair<N, Double>> { a, b -> a.second.compareTo(b.second) }

        while(unvisitedSet.isNotEmpty() && unvisitedSet.contains(to)) {
            val neighbors = graph.edges(current).filter { unvisitedSet.contains(it.to.key) }
            neighbors.forEach { (d, node) ->
                val distance = d.toDouble()
                val next = node.key
                if (distances[current]!! + distance < distances[next]!!) {
                    distances[next] = distances[current]!! + distance
                    adjacency[next] = current
                }
                frontier.add(next to distances[next]!!)
            }

            unvisitedSet -= current
            frontier.removeIf { it.first == current }

            if (current == to) {
                break
            }


            if (frontier.isNotEmpty()) {
                current = frontier.poll().first
            }
        }
        val path = mutableListOf(to)
        while(adjacency.containsKey(path.last())) {
            path += adjacency[path.last()]!!
        }

        return path.reversed()
    }

    override fun <N: Number> shortestPath(grid: NumberGrid<N>, from: Point, to: Point) : List<Point> =
        shortestPath(grid.toGraph(), from, to)
}