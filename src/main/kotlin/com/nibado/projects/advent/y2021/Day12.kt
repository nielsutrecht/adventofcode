package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import com.nibado.projects.advent.graph.Graph

object Day12 : Day {
    val values = resourceLines(2021, 12).map { it.split('-').let { (a, b) -> Cave(a) to Cave(b) } }
    val graph = values.fold(Graph<Cave, Int>()) { graph, (from, to) -> graph.add(from, to, 0, true);graph }

    override fun part1() : Int {
        val start = Cave("start")
        val end = Cave("end")
        val paths = mutableListOf<List<Cave>>()

        subPath(start, end, emptySet(), emptyList(), paths)

        return paths.size
    }

    private fun subPath(from: Cave, to: Cave, visited: Set<Cave>, path: List<Cave>, paths: MutableList<List<Cave>>) {
        if(from == to) {
            paths += path
            //println(path.map { it.id }.joinToString(","))
            return
        }
        val newVisited = if(!from.big) {
            visited + from
        } else visited

        graph.nodes(from).filterNot { it.key in visited }.forEach {
            subPath(it.key, to, newVisited, path + it.key, paths)
        }
    }

    override fun part2() : Int {
        val start = Cave("start")
        val end = Cave("end")
        val paths = mutableListOf<List<Cave>>()

        subPath2(start, end, emptyMap(), emptyList(), paths)

        return paths.size
    }

    private fun subPath2(from: Cave, to: Cave, visited: Map<Cave, Int>, path: List<Cave>, paths: MutableList<List<Cave>>) {
        if(from == to) {
            paths += path
            //println(path.map { it.id }.joinToString(","))
            return
        }

        val newVisited = if(!from.big) {
            visited + (from to visited.getOrDefault(from, 0) + 1)
        } else visited

        fun canVisit(cave: Cave) : Boolean {
            val max = if(newVisited.any { it.value >= 2 }) 1 else 2
            return newVisited.getOrDefault(cave, 0) < max
        }

        graph.nodes(from).filterNot { it.key.id == "start" }.filter { canVisit(it.key) }.forEach {
            subPath2(it.key, to, newVisited, path + it.key, paths)
        }
    }

    data class Cave(val id: String, val big: Boolean = id.uppercase() == id) {
        override fun hashCode(): Int = id.hashCode()
        override fun equals(other: Any?) = if(other !is Cave || other == null) false else other.id == id
    }
}

fun main() {
    println(Day12.part1())
    println(Day12.part2())
}