package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*

object Day14 : Day {
    private val values = resourceStrings(2021, 14).let { (template, rules) ->
        template to
                rules.split("\n").map { it.split(" -> ").let { (from, to) -> from to to } }.toMap()
    }

   private fun solve(iterations: Int): Long {
        val current = values.first.windowed(2).groupBy { it }.map { (k, v) -> k to v.size.toLong() }.toMap().toMutableMap()
        val elements = values.first.groupBy { it.toString() }.map { (k, v) -> k to v.size.toLong() }.toMap().toMutableMap()

        repeat(iterations) {
            val mods = mutableMapOf<String, Long>()
            values.second.forEach { (key, replacement) ->
                if ((current[key] ?: 0) > 0) {
                    val occurrences = current[key]!!
                    mods[key] = (mods[key] ?: 0) - occurrences
                    val (left, right) = (key[0] + replacement) to (replacement + key[1])
                    mods[left] = (mods[left] ?: 0) + occurrences
                    mods[right] = (mods[right] ?: 0) + occurrences
                    elements[replacement] = (elements[replacement] ?: 0) + occurrences
                }
            }

            mods.forEach { (k, v) ->
                current[k] = (current[k] ?: 0) + v
            }
        }
        return elements.values.let { it.maxOrNull()!! - it.minOrNull()!! }
    }

    override fun part1(): Long = solve(10)
    override fun part2(): Long = solve(40)
}