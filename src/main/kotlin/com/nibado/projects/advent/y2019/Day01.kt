package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day01 : Day {
    private val modules = resourceLines(2019, 1).map { it.toInt() }

    override fun part1() = modules.map(::fuel).sum()
    override fun part2() = modules.map(::fuelIterative).sum()

    private fun fuel(mass: Int) = mass  / 3 - 2

    private fun fuelIterative(mass: Int) : Int {
        var current = fuel(mass)
        var total = current
        while(current >= 0) {
            current = fuel(current)
            if(current > 0) {
                total += current
            }
        }

        return total
    }
}