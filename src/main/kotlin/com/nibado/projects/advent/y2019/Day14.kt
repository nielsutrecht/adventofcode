package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.graph.Graph
import com.nibado.projects.advent.resourceLines
import kotlin.math.ceil

typealias ElementAmount = Pair<String, Int>

object Day14 : Day {
    private val reactionsReal = resourceLines(2019, 14).map(::parseReaction)
    private val reactions = INPUT_A.trim().split("\n").map(::parseReaction)

    override fun part1(): Int {
        val map = reactions.map { it.output.first to it }.toMap()

        //val oreCost = oreCost("FUEL", 1, map, emptyMap<>())

        TODO()

        //return oreCost
    }

    fun oreCost(
            current: String,
            requiredAmount: Int,
            map: Map<String, Reaction>,
            stock: MutableMap<String, Int>,
            depth: Int = 0): Int {

        val start = map.getValue(current)

        val pre = "  ".repeat(depth)

        println("$pre$current: $requiredAmount")

        var sum = 0
        for(i in start.input) {
            val currentAmount = stock[i.first] ?: 0

            val requiredReactions = ceil(i.second.toDouble() / start.output.second.toDouble()).toInt()

            println("$pre- ${i.first} (${i.second} > ${start.output.second}): $requiredReactions")

            sum += if(i.first == "ORE") {
                requiredReactions * i.second
            } else {
                oreCost(i.first, requiredReactions * i.second, map, stock, depth + 1)
            }
        }

        return sum
    }


    fun print(list: List<Reaction>) {

    }

    override fun part2() = TODO()

    fun parseReaction(line: String): Reaction {
        fun parseElementAmount(inp: String) = inp.split(", ")
                .map { it.split(" ").let { (a, e) -> e to a.toInt() } }

        val (input, output) = line.split(" => ")
                .let { (input, output) -> parseElementAmount(input) to parseElementAmount(output).first() }

        return Reaction(input, output)
    }
}

data class Reaction(val input: List<ElementAmount>, val output: ElementAmount)

fun main() {
    println(Day14.part1())
}

const val INPUT_A = """
10 ORE => 10 A
1 ORE => 1 B
7 A, 1 B => 1 C
7 A, 1 C => 1 D
7 A, 1 D => 1 E
7 A, 1 E => 1 FUEL
"""
