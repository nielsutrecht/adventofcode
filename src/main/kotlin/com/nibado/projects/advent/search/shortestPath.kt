package com.nibado.projects.advent.search

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.NumberGrid
import com.nibado.projects.advent.graph.Graph

interface GraphShortestPath {
    fun <N, E: Number> shortestPath(graph: Graph<N, E>, from: N, to: N) : List<N>
}

interface GridShortestPath {
    fun <N: Number> shortestPath(grid: NumberGrid<N>, from: Point, to: Point) : List<Point>
}