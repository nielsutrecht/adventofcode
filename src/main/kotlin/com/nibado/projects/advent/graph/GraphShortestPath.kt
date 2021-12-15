package com.nibado.projects.advent.graph

interface GraphShortestPath {
    fun <N, E: Number> search(graph: Graph<N, E>, from: N, to: N) : List<N>
}