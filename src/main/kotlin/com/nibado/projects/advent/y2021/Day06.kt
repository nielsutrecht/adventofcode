package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day06 : Day {
    private val values = resourceString(2021, 6).split(',').map { it.toInt() }

    private fun simulate(turns: Int) : Long {
        var fish = values.groupBy { it }.map { (k, v) -> k to v.size.toLong() }.toMap().toMutableMap()
        repeat(turns) {
            val updates = fish.map { (age, amount) ->
                if(age == 0) (6 to amount) else (age - 1 to amount)
            } + (8 to (fish[0] ?: 0))
            fish.clear()
            updates.forEach { (age, amount) -> fish[age] = (fish[age] ?: 0) + amount  }
        }

        return fish.values.sum()
    }

    override fun part1() = simulate(80)
    override fun part2() = simulate(256)
}