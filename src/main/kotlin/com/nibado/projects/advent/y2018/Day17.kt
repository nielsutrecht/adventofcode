package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.Point
import com.nibado.projects.advent.Rectangle
import com.nibado.projects.advent.collect.CharMap
import com.nibado.projects.advent.resourceRegex
import java.util.*
import kotlin.math.max

object Day17 : Day {
    private val regex = "(x|y)=([0-9]+), (x|y)=([0-9]+)\\.\\.([0-9]+)".toRegex()

    private val input = resourceRegex(2018, 17, regex)
            .map { it.drop(1) }
            .map { (a1, v1, a2, v2a, v2b) -> Scan(a1[0], v1.toInt(), a2[0], IntRange(v2a.toInt(), v2b.toInt())) }

    val map : CharMap by lazy { buildMap().also { flow(it) } }

    private fun buildMap(): CharMap {
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

    private fun flow(map: CharMap) {
        val down = Point(0, 1)
        val consider = Stack<Point>()

        consider += Point(500, 0) + down

        while (consider.isNotEmpty()) {
            val cur = consider.pop()

            map[cur] = '|'

            if (!map.inBounds(cur + down)) {
                continue
            }

            if (map.canPlace(cur + down)) {
                consider += cur + down
                continue
            } else {
                val flow = map.flow(cur)
                val c = if (flow.contained) '~' else '|'
                flow.points.forEach { map[it] = c }

                if (flow.contained) {
                    if (map.inBounds(cur.up())) {
                        consider += cur.up()
                    }
                } else {
                    consider += flow.points.filter { map.inBounds(it.down()) && map[it.down()] == '.' }
                }
            }
        }
    }

    private fun CharMap.flow(p: Point): Flow {
        val points = mutableListOf<Point>()

        points += p

        fun scan(p: Point, dir: Point, points: MutableList<Point>) : Boolean {
            var cur = p
            while(true) {
                cur += dir
                if(!inBounds(cur)) {
                    return false
                }
                if(get(cur) == '#') {
                    return true
                }
                val below = cur.down()
                if(inBounds(below) && get(below) in setOf('#', '~')) {
                    points += cur
                } else if(get(below) != '|'){
                    points += cur
                    return false
                } else {
                    return false
                }
            }
        }

        val wallLeft = scan(p, Point(-1, 0), points)
        val wallRight = scan(p, Point(1, 0), points)

        return Flow(points, wallLeft && wallRight)
    }

    private fun CharMap.canPlace(p: Point) = inBounds(p) && this[p] == '.'
    private fun CharMap.minY() = map.points { c -> c == '#' }.minByOrNull { it.y }!!.y

    override fun part1() = map.minY().let { minY -> map.points { it == '~' || it == '|'}.count { it.y >= minY } }
    override fun part2() = map.minY().let { minY -> map.points { it == '~'}.count { it.y >= minY } }

    data class Scan(val axis1: Char, val value1: Int, val axis2: Char, val value2: IntRange) {
        fun rect() =
                if (axis1 == 'x') {
                    Rectangle(Point(value1, value2.start), Point(value1, value2.endInclusive))
                } else {
                    Rectangle(Point(value2.start, value1), Point(value2.endInclusive, value1))
                }
    }

    data class Flow(val points: List<Point>, val contained: Boolean)
}