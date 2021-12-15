package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.NumberGrid
import com.nibado.projects.advent.graph.*

object Dijkstra : GraphShortestPath {
    override fun <N, E : Number> search(graph: Graph<N, E>, from: N, to: N): List<N> {
        TODO("Unfinished")



        return emptyList()
    }

    fun <N: Number> search(grid: NumberGrid<N>, from: Point, to: Point) : Map<Point, Pair<List<Point>, Double>> {
        val unvisitedSet = grid.points.toMutableSet()
        val distances = grid.points.map { it to Double.POSITIVE_INFINITY }.toMap().toMutableMap()
        val paths = mutableMapOf<Point, List<Point>>()
        distances[from] = 0.0

        var current = from

        while (unvisitedSet.isNotEmpty() && unvisitedSet.contains(to)) {
            current.neighborsHv().filter { grid.inBound(it) }.forEach { adjacent ->
                val distance = grid[adjacent].toDouble()
                if (distances[current]!! + distance < distances[adjacent]!!) {
                    distances[adjacent] = distances[current]!! + distance
                    paths[adjacent] = paths.getOrDefault(current, listOf(current)) + listOf(adjacent)
                }
            }

            unvisitedSet.remove(current)
            if(unvisitedSet.size % 100 == 0) {
                println(unvisitedSet.size)
            }
            if (current == to || unvisitedSet.all { distances[it]!!.isInfinite() }) {
                break
            }

            if (unvisitedSet.isNotEmpty()) {
                current = unvisitedSet.minByOrNull { distances[it]!! }!!
            }
        }

        return paths.mapValues { entry -> entry.value to distances[entry.key]!! }
    }
}