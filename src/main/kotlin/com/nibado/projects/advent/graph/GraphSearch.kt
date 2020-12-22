package com.nibado.projects.advent.graph

private typealias Node<N> = Graph.Node<N>

object GraphSearch {
    fun <N,E> path(graph: Graph<N,E>, from: N, to: N) : List<N> {
        val frontier = mutableSetOf<N>()
        val cameFrom = mutableMapOf<N, N>()

        frontier += from

        while(frontier.isNotEmpty()) {
            val current = frontier.firstOrNull()!!
            frontier -= current

            val next = graph.nodes(current)
                    .filterNot { cameFrom.containsKey(it.key) }

            frontier += next.map { it.key }
            next.forEach { cameFrom[it.key] = current }

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

    fun <N, E> nodes(graph: Graph<N,E>, from: N) : List<Node<N>> {
        val start = graph[from]

        val visited = mutableSetOf<N>()
        val frontier = mutableSetOf<N>()

        frontier += start.key
        visited += start.key

        while(frontier.isNotEmpty()) {
            val current = frontier.first()
            frontier.remove(current)

            val nodes = graph.nodes(current).filterNot { visited.contains(it.key) }.map { it.key }

            visited += nodes
            frontier += nodes
        }

        return visited.map { graph[it] }
    }

    data class Path<N,E>(val nodes: List<Pair<Graph.Edge<N, E>, Graph.Node<N>>>) {
        fun weight(f: (E) -> Int) : Int = nodes.map { (e) -> f(e.value) }.sum()
    }
}
