package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import com.nibado.projects.advent.graph.Graph

object Day12 : Day {
    private val graph = resourceLines(2021, 12).map { it.split('-').let { (a, b) -> Cave(a) to Cave(b) } }
        .fold(Graph<Cave, Int>()) { graph, (from, to) -> graph.add(from, to, 0, true);graph }

    override fun part1() = graph.paths(Cave("start"), Cave("end")) { node, visited ->
        node.key.big || visited.getOrDefault(node, 0) < 1 }.size

    override fun part2() = graph.paths(Cave("start"), Cave("end")) { node, visited ->
        when {
            node.key.id == "start" -> false
            node.key.big -> true
            else -> visited.getOrDefault(node, 0) < if (visited.any { (node, count) -> !node.key.big && count >= 2 }) 1 else 2
        } }.size

    data class Cave(val id: String, val big: Boolean = id.uppercase() == id)
}