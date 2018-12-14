package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day

object Day14 : Day {
    private const val PUZZLE_INPUT = 330121
    private const val start = "37"

    override fun part1(): String {
        val elves = mutableListOf(0, 1)
        val recipes = StringBuilder(start)

        while (recipes.length < PUZZLE_INPUT + 10) {
            recipes(recipes, elves)
        }

        return recipes.substring(PUZZLE_INPUT, PUZZLE_INPUT + 10)
    }

    override fun part2(): Int {
        val num = PUZZLE_INPUT.toString()
        val elves = mutableListOf(0, 1)
        val recipes = StringBuilder(30000000)
        recipes.append(start)

        while(true) {
            recipes(recipes, elves)

            val lastBit = if(recipes.length > 10) recipes.subSequence(recipes.length - 10, recipes.length) else ""

            if(lastBit.contains(num)) {
                return(recipes.length - 10 + lastBit.indexOf(num) )
            }
        }
    }

    private fun recipes(recipes: StringBuilder, elves: MutableList<Int>) {
        recipes.append(elves.indices.map { recipes[elves[it]] - '0' }.sum())
        elves[0] = ((elves[0] + (recipes[elves[0]] - '0') + 1) % recipes.length)
        elves[1] = ((elves[1] + (recipes[elves[1]] - '0') + 1) % recipes.length)
    }
}