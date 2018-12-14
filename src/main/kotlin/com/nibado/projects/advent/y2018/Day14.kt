package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day

object Day14 : Day {
    private const val PUZZLE_INPUT = 330121
    private val start = "37".map { it - '0' }

    override fun part1(): String {
        val elves = mutableListOf(0, 1)
        val recipes = start.toMutableList()

        while (recipes.size < PUZZLE_INPUT + 10) {
            recipes(recipes, elves)
        }

        return recipes.drop(PUZZLE_INPUT).take(10).joinToString("")
    }

    override fun part2(): Int {
        val num = PUZZLE_INPUT.toString()
        val elves = mutableListOf(0, 1)
        val recipes = start.toMutableList()

        while(true) {
            recipes(recipes, elves)

            val lastBit = if(recipes.size > 10) recipes.subList(recipes.size - 10, recipes.size).joinToString("") else ""

            if(lastBit.contains(num)) {
                return(recipes.size - 10 + lastBit.indexOf(num) )
            }
        }
    }

    private fun recipes(recipes: MutableList<Int>, elves: MutableList<Int>) {
        recipes += elves.indices.map { recipes[elves[it]] }.sum().toString().map { it - '0' }
        elves[0] = ((elves[0] + recipes[elves[0]] + 1) % recipes.size)
        elves[1] = ((elves[1] + recipes[elves[1]] + 1) % recipes.size)
    }
}