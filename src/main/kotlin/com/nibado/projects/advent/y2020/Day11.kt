package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.CharMap

object Day11 : Day {
    private val map = resourceString(2020, 11)

    override fun part1(): Int = solve({ map, p -> map.count(p) == 0 }, { map, p -> map.count(p) >= 4 })
    override fun part2(): Int = solve({ map, p -> map.countRays(p) == 0 }, { map, p -> map.countRays(p) >= 5 })

    private fun solve(occRule: (CharMap, Point) -> Boolean,
                      emptyRule: (CharMap, Point) -> Boolean): Int {

        var map = CharMap.from(map)
        var count: Int
        do {
            count = map.count('#')
            map = round(map, occRule, emptyRule)
        } while(count != map.count('#'))

        return count
    }

    private fun round(map: CharMap,
                      occRule: (CharMap, Point) -> Boolean,
                      emptyRule: (CharMap, Point) -> Boolean): CharMap {
        val newMap = CharMap(map.width, map.height, fill = '.')

        map.points { it != '.' }.forEach {
            newMap[it] = map[it]
            if (map[it] == 'L' && occRule(map, it)) {
                newMap[it] = '#'
            }
            if (map[it] == '#' && emptyRule(map, it)) {
                newMap[it] = 'L'
            }
        }

        return newMap
    }

    private fun CharMap.count(p: Point): Int = p.neighbors().filter { this.inBounds(it) }.count { this[it] == '#' }
    private fun CharMap.countRays(p: Point): Int = Point.NEIGHBORS
                    .mapNotNull { n -> Point.ray(n)
                            .map { it + p }
                            .takeWhile { this.inBounds(it) }
                            .firstOrNull { this[it] != '.' } }
                    .count { this[it] == '#' }
}
