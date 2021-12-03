package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day03 : Day {
    private val values = resourceLines(2021, 3).map { it.toCharArray().map { it.digitToInt() } }

    private fun List<List<Int>>.mostCommon() = first().indices
        .map { idx -> count { it[idx] == 1 } }
        .map { if(it >= size - it) 1 else 0 }

    override fun part1() : Any {
        val binary = values.mostCommon()
        val inverted = binary.map { it xor 1 }

        return binary.joinToString("").toLong(2) * inverted.joinToString("").toLong(2)
    }

    override fun part2() : Any {
        fun solve(test: (Int, Int) -> Boolean) : Long {
            var rating = values.toMutableList()
            var bitIndex = 0
            while(rating.size > 1) {
                val most = rating.mostCommon()[bitIndex]

                rating.removeIf { test(it[bitIndex], most) }
                bitIndex++
            }
            return rating.first().joinToString("").toLong(2)
        }

        return solve { a, b -> a != b } * solve { a, b -> a == b }
    }
}