package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import com.nibado.projects.advent.graph.Graph

object Day12 : Day {
    private val graph = resourceLines(2021, 12).map { it.split('-').let { (a, b) -> Cave(a) to Cave(b) } }
        .fold(Graph<Cave, Int>()) { graph, (from, to) -> graph.add(from, to, 0, true);graph }

    override fun part1(): Int = solve { node, visited -> visited.getOrDefault(node.key, 0) < 1 }

    override fun part2(): Int = solve { node, visited ->
        visited.getOrDefault(node.key, 0) < if (visited.any { it.value >= 2 }) 1 else 2
    }

    private fun solve(nodeFilter: (Graph.Node<Cave>, Map<Cave, Int>) -> Boolean): Int = mutableListOf<List<Cave>>()
        .also { recurse(Cave("start"), Cave("end"), emptyMap(), emptyList(), it, nodeFilter) }.size

    private fun recurse(
        from: Cave, to: Cave, visited: Map<Cave, Int>, path: List<Cave>, paths: MutableList<List<Cave>>,
        nodeFilter: (Graph.Node<Cave>, Map<Cave, Int>) -> Boolean
    ) {
        if (from == to) {
            paths += path
            return
        }

        val newVisited = if (!from.big) {
            visited + (from to visited.getOrDefault(from, 0) + 1)
        } else visited

        graph.nodes(from).filterNot { it.key.id == "start" }.filter { nodeFilter(it, newVisited) }.forEach {
            recurse(it.key, to, newVisited, path + it.key, paths, nodeFilter)
        }
    }

    data class Cave(val id: String, val big: Boolean = id.uppercase() == id)
}