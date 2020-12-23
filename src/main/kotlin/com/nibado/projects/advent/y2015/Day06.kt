package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.*

object Day06 : Day {
    private val values = resourceRegex(2015, 6, "(turn on|turn off|toggle) ([0-9,]+) through ([0-9,]+)").map {
        it.drop(1).let { (inst, left, right) -> inst to Rectangle.parse(left, right).points().toSet() }
    }

    override fun part1(): Int {
        val lights = mutableMapOf<Point, Boolean>()

        values.forEach { (inst, rect) ->
            when(inst) {
                "turn on" -> rect.forEach { lights[it] = true }
                "turn off" -> rect.forEach { lights[it] = false }
                "toggle" -> rect.forEach { lights[it] = !lights.getOrDefault(it, false) }
            }
        }

        return lights.count { it.value }
    }

    override fun part2() : Int {
        val lights = mutableMapOf<Point, Int>()

        values.forEach { (inst, rect) ->
            when(inst) {
                "turn on" -> rect.forEach { lights[it] = lights.getOrDefault(it, 0) + 1 }
                "turn off" -> rect.forEach { lights[it] = maxOf(lights.getOrDefault(it, 0) - 1, 0) }
                "toggle" -> rect.forEach { lights[it] = lights.getOrDefault(it, 0) + 2 }
            }
        }

        return lights.values.sum()
    }
}