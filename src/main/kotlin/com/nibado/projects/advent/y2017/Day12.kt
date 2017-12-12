package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceRegex

object Day12 : Day {
    private val input = resourceRegex(12, Regex("^([0-9]+) <-> ([0-9 ,]+)$")).map { Pair(it[1].toInt(), it[2].split(", ").map { it.toInt() }) }
    private val solution : Map<Int, Int> by lazy { solve(connections()) }

    private fun connections(): Map<Int, Set<Int>> {
        val map = mutableMapOf<Int, MutableSet<Int>>()

        fun get(id: Int) = map.computeIfAbsent(id, { mutableSetOf()})

        input.forEach {
            a -> a.second.forEach{ b ->
                get(a.first).add(b)
                get(b).add(a.first)
            }
        }

        return map
    }

    private fun solve(connections: Map<Int, Set<Int>>): Map<Int, Int> {
        val visited = mutableSetOf<Int>()
        val groups = mutableMapOf<Int, Int>()

        while(connections.size > visited.size) {
            val subVisited = mutableSetOf<Int>()
            val group = connections.keys.filterNot { visited.contains(it) }.sorted().first()

            fill(group, connections, subVisited)

            groups[group] = subVisited.size
            visited += subVisited
        }

        return groups
    }

    private fun fill(current: Int, nodes: Map<Int, Set<Int>>, visited: MutableSet<Int>) {
        visited += current

        nodes[current]!!.filterNot { visited.contains(it) }.forEach {
            fill(it, nodes, visited)
        }
    }

    override fun part1() = solution[0]!!.toString()
    override fun part2() = solution.size.toString()
}