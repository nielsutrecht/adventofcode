package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.Rectangle
import com.nibado.projects.advent.collect.CharMap
import com.nibado.projects.advent.resourceRegex
import kotlin.math.max

object Day17 : Day {
    private val regex = "(x|y)=([0-9]+), (x|y)=([0-9]+)\\.\\.([0-9]+)".toRegex()

    private val input = resourceRegex(2018, 18, regex)
            .map { it.drop(1) }
            .map { (a1, v1, a2, v2a, v2b) -> Scan(a1[0], v1.toInt(), a2[0], IntRange(v2a.toInt(), v2b.toInt())) }

    fun buildMap(): CharMap {
        val max = input.map { it.rect().right }.reduce { a, p -> Point(max(a.x, p.x), max(a.y, p.y)) }

        val map = CharMap(max.x + 1, max.y + 1, '.', '#')

        for (s in input) {
            if (s.axis1 == 'x') {
                map.drawVertical(s.value1, s.value2)
            } else {
                map.drawHorizontal(s.value1, s.value2)
            }
        }

        return map
    }

    fun drop(map: CharMap) {
        val down = Point(0, 1)
        var p = Point(500, 0)

        while (true) {
            p += down
            val below = p + down

            if(map.isEq(below, '#')) {
                map[p] = '~'
                println("WA: Set $p to ~")
                break
            } else if(map.isEq(below, '~')) {
                val nearestEmpty = nearestEmpty(map, below)
                if(nearestEmpty != null) {
                    map[nearestEmpty] = '~'
                    println("NE: Set $nearestEmpty to ~")
                } else {
                    map[p] = '~'
                    println("NNE: Set $p to ~")
                }
                break
            } else {
                map[p] = '|'
            }
        }
    }

    fun nearestEmpty(map: CharMap, cur: Point) : Point? {

        if(map.isEq(cur, '.')) {
            return cur
        }
        var leftWall = false
        var rightWall = false
        for(d in (1 .. map.width)) {
            if(rightWall && leftWall) {
                return null
            }

            if(!leftWall) {
                val left = cur.copy(x = cur.x - d)

                if(map.isEq(left, '#')) {
                    leftWall = true
                    continue
                }

                if (map.isEq(left, '.')) {
                    return left
                }
            }

            if(!rightWall) {
                val right = cur.copy(x = cur.x + d)
                if(map.isEq(right, '#')) {
                    rightWall = true
                    continue
                }
                if (map.isEq(right, '.')) {
                    return right
                }
            }
        }
        return null
    }

    override fun part1(): Int {
        val map = buildMap()

        for(i in 1 .. 18) {
            drop(map)
        }

        println(map.toString(Point(494, 0)))
        return 0
    }

    override fun part2(): Int {
        return 0
    }

    data class Scan(val axis1: Char, val value1: Int, val axis2: Char, val value2: IntRange) {
        fun rect() =
                if (axis1 == 'x') {
                    Rectangle(Point(value1, value2.start), Point(value1, value2.endInclusive))
                } else {
                    Rectangle(Point(value2.start, value1), Point(value2.endInclusive, value1))
                }
    }
}

fun main(args: Array<String>) {
    println(Day17.part1())
}