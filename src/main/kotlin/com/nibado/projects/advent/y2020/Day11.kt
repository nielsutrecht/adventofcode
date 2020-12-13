package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.CharMap

object Day11 : Day {
    private val map = resourceString(2020, 11)

    override fun part1(): Int {
        var map = CharMap.from(map)

        var count = map.count('#')
        while(true) {
            map = round1(map)
            val newCount = map.count('#')
            if(newCount == count) {
                break
            } else {
                count = newCount
            }
        }

        return count
    }

    override fun part2(): Int {
        var map = CharMap.from(map)
        var count = map.count('#')
        while(true) {
            map = round2(map)
            val newCount = map.count('#')
            if(newCount == count) {
                break
            } else {
                count = newCount
            }

        }

        return count
    }

    private fun solve(occRule: (CharMap, Point) -> Boolean,
                      emptyRule: (CharMap, Point) -> Boolean) : Int {

        var map = CharMap.from(map)
        var count = map.count('#')
        while(true) {
            map = round(map, occRule, emptyRule)
            val newCount = map.count('#')
            if(newCount == count) {
                break
            } else {
                count = newCount
            }

        }

        return count
    }

    private fun round(map: CharMap,
                      occRule: (CharMap, Point) -> Boolean,
                      emptyRule: (CharMap, Point) -> Boolean) : CharMap {
        val newMap = CharMap(map.width, map.height, fill = '.')

        map.points { it != '.'}.forEach {
            newMap[it] = map[it]
            if(map[it] == 'L' && occRule(map, it)) {
                newMap[it] = '#'
            }
            if(map[it] == '#' && emptyRule(map, it)) {
                newMap[it] = 'L'
            }
        }

        return newMap
    }

    private fun round1(map: CharMap) : CharMap {
        val newMap = CharMap(map.width, map.height, fill = '.')

        map.points { it != '.'}.forEach {
            newMap[it] = map[it]
            if(map[it] == 'L' && map.count(it) == 0) {
                newMap[it] = '#'
            }
            if(map[it] == '#' && map.count(it) >= 4) {
                newMap[it] = 'L'
            }
        }

        return newMap
    }

    private fun round2(map: CharMap) : CharMap {
        val newMap = CharMap(map.width, map.height, fill = '.')

        map.points { it != '.'}.forEach {
            newMap[it] = map[it]
            if(map[it] == 'L' && map.countRays(it) == 0) {
                newMap[it] = '#'
            }
            if(map[it] == '#' && map.countRays(it) >= 5) {
                newMap[it] = 'L'
            }
        }

        return newMap
    }

    private fun CharMap.count(p: Point) : Int =
            p.neighbors().filter { this.inBounds(it) }.count { this[it] == '#' }

    private fun CharMap.countRays(p: Point) : Int  =
        Point.NEIGHBORS
                .mapNotNull { ray(it).map { it + p }.takeWhile { this.inBounds(it) }.firstOrNull { this[it] != '.' } }
                .count { this[it] == '#' }

    private fun ray(step: Point) = sequence {
        var current = step

        while(true) {
            yield(current)
            current += step
        }
    }
}

fun main() {
    println(Day11.part2())
}
