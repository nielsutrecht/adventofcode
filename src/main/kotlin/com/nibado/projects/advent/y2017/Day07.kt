package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

val regex = Regex("([a-z]{4,8}) \\(([0-9]+)\\)( -> ([a-z ,]+))?")

object Day07 : Day {
    val tree : Tree by lazy { parseTree(resourceLines(7)) }

    override fun part1() = tree.name
    override fun part2() = "2"
}

/*
nzeqmqi inwmb 12216 15378
nmhmw nzeqmqi 979 1051
pknpuej nzeqmqi 91 1051
rfkvap nzeqmqi 655 1060
 */

fun walk(tree: Tree) {
    //If tree is unbalanced but children are balanced, return the problem child
    if(!tree.balanced()) {
        tree.children().map { walk(it) }
        if(tree.children().map { it.balanced() }.count { it } == tree.children().size) {
            print(tree)
            tree.children().forEach { print(it) }

            //tree.children().partition { it.sum() }
        }
    }
}

fun parseTree(lines: List<String>) : Tree {
    val input = lines.map { parse(it) }.toList()
    val programs = input.map { it.name to Tree(it.name, it.weight, null) }.toMap()

    input.flatMap { a -> a.programs.map { p -> Pair(a.name, p) } }.forEach {
        programs[it.first]!!.nodes[it.second] = programs[it.second]!!
        programs[it.second]!!.parent = programs[it.first]!!
    }

    val root = programs.values.filter { it.parent == null }.first()

    walk(root)

    return root
}

fun print(tree: Tree) {
    println("${tree.name} ${tree.parent?.name} ${tree.weight} ${tree.sum()} ${tree.balanced()}")
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
    fun sum() = weight + childSum()
    fun childSum() : Int = nodes.values.map { it.sum() }.sum()
    fun balanced() = nodes.values.map { it.sum() }.toSet().size == 1
}