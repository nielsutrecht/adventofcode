package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

val regex = Regex("([a-z]{4,8}) \\(([0-9]+)\\)( -> ([a-z ,]+))?")

object Day07 : Day {
    val tree : Tree by lazy { parseTree(resourceLines(7)) }

    override fun part1() = tree.name
    override fun part2() = walk(tree).toString()
}

fun walk(tree: Tree) : Int {
    if(!tree.balanced()) {
        val result = tree.children().map { walk(it) }.max()
        if(tree.children().map { it.balanced() }.count { it } == tree.children().size) {
            val groups = tree.children().groupBy { it.sum() }
            val wrongTree = groups.values.first { it.size == 1 }.first()
            val correctTree = groups.values.first { it.size > 1 }.first()

            return wrongTree.weight - (wrongTree.sum() - correctTree.sum())
        }

        return result!!
    }

    return Int.MIN_VALUE
}

fun parseTree(lines: List<String>) : Tree {
    val input = lines.map { parse(it) }.toList()
    val programs = input.map { it.name to Tree(it.name, it.weight, null) }.toMap()

    input.flatMap { a -> a.programs.map { p -> Pair(a.name, p) } }.forEach {
        programs[it.first]!!.nodes[it.second] = programs[it.second]!!
        programs[it.second]!!.parent = programs[it.first]!!
    }

    return programs.values.filter { it.parent == null }.first()
}

fun parse(line: String): ProgramOutput {
    val result = regex.matchEntire(line)!!

    val name = result.groups.get(1)!!.value
    val weight = result.groups.get(2)!!.value.toInt()
    val programs = if(result.groups.get(4) == null) listOf() else result.groups.get(4)!!.value.split(", ").toList()

    return ProgramOutput(name, weight, programs)
}

data class ProgramOutput(val name: String, val weight: Int, val programs: List<String>)

data class Tree (val name: String, val weight: Int, var parent: Tree?) {
    val nodes: MutableMap<String, Tree> = mutableMapOf()

    fun children() = nodes.values
    fun sum(): Int = weight + nodes.values.map { it.sum() }.sum()
    fun balanced() = nodes.values.map { it.sum() }.toSet().size == 1
}