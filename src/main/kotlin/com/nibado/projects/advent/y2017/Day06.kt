package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day

object Day06 : Day {
    val solution: Solution by lazy { solve(mutableListOf(2, 8, 8, 5, 4, 2, 3, 1, 5, 5, 1, 2, 15, 13, 5, 14)) }

    override fun part1() = solution.firstCycle
    override fun part2() = solution.loop

    fun solve(banks: MutableList<Int>): Solution {
        val seen: MutableSet<List<Int>> = mutableSetOf()
        var cycle = 0
        var seenBefore: List<Int>? = null
        var firstCycle = 0

        while(true) {
            seen.add(banks.toList())

            cycle++
            var (index, value) = banks.withIndex().maxBy {it.value}!!

            banks[index] = 0

            for(i in 0 until value) {
                banks[++index % banks.size]++
            }

            if(seen.contains(banks)) {
                if(seenBefore == null) {
                    firstCycle = cycle
                    seenBefore = banks.toList()
                } else {
                    if(seenBefore == banks) {
                        return Solution(firstCycle, cycle - firstCycle)
                    }
                }
            }
        }
    }

    data class Solution(val firstCycle: Int, val loop: Int)
}