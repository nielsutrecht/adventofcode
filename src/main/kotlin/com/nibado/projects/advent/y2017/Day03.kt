package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day

object Day03 : Day {
    override fun main(args: Array<String>) {
        val input = 277678

        part1(input)
        part2(input)
    }

    fun part1(input: Int) {
        var remainder = input
        var ring = 0
        while(remainder > 0) {
            val size = if (ring == 0) 1 else size(ring) - size(ring - 1)

            if(remainder <= size) {
                break
            }

            remainder -= size
            ring++
        }

        var x = ring
        var y = ring

        for(i in 0 .. (remainder - 1)) {
            val l = i / (ring * 2)
            when(l) {
                0 -> y--
                1 -> x--
                2 -> y++
                3 -> x++
            }
        }

        println(Math.abs(x) + Math.abs(y))
    }

    fun part2(input: Int) {
        val map = mutableMapOf<Point, Int?>()
        var r = 0
        var current = Point(0,0)
        map.put(current, 1)
        var count = 0
        while(true) {
            var points = ring(r)
            current = Point(r, r)

           for(p in points) {
                current = current.add(p)
                count++

                if(!map.containsKey(current)) {
                    val sum = current.neighbors().map { map.getOrDefault(it,0)!! }.sum()

                    if(sum > input) {
                        println(sum)
                        return
                    } else {
                        map.put(current, sum)
                    }
                }
            }

            r++
        }
    }
}

data class Point(val x: Int, val y: Int) {
    fun add(other: Point): Point =
         Point(this.x + other.x, this.y + other.y)

    fun neighbors() : List<Point> = listOf(
            Point(-1, -1),
            Point(0, -1),
            Point(1, -1),
            Point(-1, 0),
            Point(1, 0),
            Point(-1, 1),
            Point(0, 1),
            Point(1, 1)
    ).map { this.add(it) }
}

fun ring(ring: Int) : List<Point> {
    if(ring == 0) {
        return listOf(Point(0, 0))
    }

    val size = if (ring == 0) 1 else size(ring) - size(ring - 1)

    return (0 until size).map { it / (ring * 2) }.map { when(it) {
        0 -> Point(0, -1)
        1 -> Point(-1, 0)
        2 -> Point(0, 1)
        3 -> Point(1, 0)
        else -> Point(0, 0)
    } }.toList()
}

fun size(ring: Int) : Int {
    val width = ring * 2 + 1
    return width * width
}