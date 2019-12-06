package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day06 : Day {
    private val orbitMap = resourceLines(2019, 6).map { it.split(")").let { (a, b) -> b to a } }.toMap()

    private fun walk(current: String) : Int =
        if(current == "COM") {
            0
        } else {
            1 + walk(orbitMap.getValue(current))
        }

    private fun path(current: String) : List<String> =
        if(current == "COM") {
            listOf("COM")
        } else {
            path(orbitMap.getValue(current)) + current
        }

    override fun part1() = orbitMap.keys.map { walk(it) }.sum()

    override fun part2() : Any {
        val you = path("YOU").toMutableList()
        val santa  = path("SAN").toMutableList()

        while(you.first() == santa.first()) {
            you.removeAt(0)
            santa.removeAt(0)
        }

        return you.size + santa.size - 2
    }
}