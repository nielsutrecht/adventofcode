package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.join

object Day06 : Day {
    val solution = solve(mutableListOf(2, 8, 8, 5, 4, 2, 3, 1, 5, 5, 1, 2, 15, 13, 5, 14))

    override fun part1() = solution.firstCycle
    override fun part2() = solution.loop

    fun solve(banks: MutableList<Int>): Solution {
        val seen: MutableSet<String> = mutableSetOf()
        var cycle = 0
        var seenBefore: String? = null
        var firstCycle = 0

        while(true) {
            seen.add(join(banks))

            cycle++
            val max = banks.max()
            var index = 0

            for(i in 0 until banks.size) {
                if(banks[i] == max) {
                    index = i
                    break
                }
            }

            var value = banks[index]
            banks[index] = 0

            while(value > 0) {
                index++
                index %= banks.size

                banks[index]++
                value--
            }

            if(seen.contains(join(banks))) {
                if(seenBefore == null) {
                    firstCycle = cycle
                    seenBefore = join(banks)
                } else {
                    if(seenBefore == join(banks)) {
                        return Solution(firstCycle, cycle - firstCycle)
                    }
                }
            }
        }
    }

    data class Solution(val firstCycle: Int, val loop: Int)
}

fun main(args: Array<String>) {
    println(Day06.part1())
    println(Day06.part2())
}