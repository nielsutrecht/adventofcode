package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day

object Day17 : Day {
    val input = 363
    override fun part1() :String {
        val buffer = mutableListOf(0)
        var current = 0
        for(i in 1 .. 2017) {
            current = (current + input) % buffer.size + 1

            buffer.add(current, i)
        }

        return buffer.get(buffer.indexOf(2017) + 1).toString()
    }

    override fun part2() :String {
        var current = 0
        var result = 0
        for(i in 1..50_000_000) {
            current = ((input + current) % i) + 1

            if(current == 1) {
                result = i
            }
        }

        return result.toString()
    }
}