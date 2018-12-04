package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day02 : Day {
    private val ids = resourceLines(2018, 2)

    override fun part1(): Int {
        val maps = ids.map {
            it.toCharArray().asSequence()
                    .groupBy { it }
                    .map { it.key to it.value.size }
                    .toMap()
        }

        val countTwo = maps.count { it.entries.any { it.value == 2 } }
        val countThree = maps.count { it.entries.any { it.value == 3 } }

        return countTwo * countThree
    }

    override fun part2() =
            ids.flatMap { i -> ids.map { i to it } }
                    .map { it.first.toCharArray().zip(it.second.toCharArray()).filter { p -> p.first == p.second }.map { p -> p.first } }
                    .first { it.size == ids[0].length - 1 }
                    .joinToString(separator = "")
}