package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.collect.CharMap
import com.nibado.projects.advent.resourceLines

object Day18 : Day {
    private val input = resourceLines(2018, 18)

    private fun sim(minutes: Int): Int {
        var map = CharMap.from(input)

        for (i in 1..minutes) {
            val nextMap = map.clone()
            for (p in map.points()) {
                val neighbors = p.neighbors().filter { map.inBounds(it) }

                fun count(c: Char) = neighbors.count { map[it] == c }

                if (map[p] == '.' && count('|') >= 3) {
                    nextMap[p] = '|'
                } else if (map[p] == '|' && count('#') >= 3) {
                    nextMap[p] = '#'
                } else if (map[p] == '#' && (count('#') < 1 || count('|') < 1)) {
                    nextMap[p] = '.'
                }
            }

            map = nextMap
        }

        return map.count('|') * map.count('#')
    }

    override fun part1() = sim(10)
    override fun part2() = sim(1000)
}