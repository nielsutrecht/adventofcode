package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.collect.FreqMap
import com.nibado.projects.advent.resourceLines

object Day06 : Day {
    private val input = resourceLines(2016, 6)

    private val maps: List<FreqMap> by lazy { Day06.map() }

    fun map() : List<FreqMap> {
        val maps = (0 until input[0].length).map { FreqMap() }

        input.forEach {
            it.forEachIndexed {i, c ->
                maps[i].inc(c)
            }
        }

        return maps
    }

    override fun part1() = maps.map { it.max()!! }.joinToString("")
    override fun part2() = maps.map { it.min()!! }.joinToString("")
}
