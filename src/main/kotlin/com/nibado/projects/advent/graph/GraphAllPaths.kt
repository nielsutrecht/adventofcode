package com.nibado.projects.advent.graph

interface GraphAllPaths {
    fun <N> paths(from: N, to: N, nodeFilter: (Graph.Node<N>, Map<Graph.Node<N>, Int>) -> Boolean) : List<List<Graph.Node<N>>>
}