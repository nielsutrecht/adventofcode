package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day

object Day04 : Day {
    private val range = (108457 .. 562041).map { it.toString().map {c -> c.toInt() } }

    private fun doesNotDecrease(password: List<Int>) = (0..4).none { password[it] > password[it + 1] }
    private fun hasSameTwo(password: List<Int>) = (0..4).any { password[it] == password[it + 1] }
    private fun hasGroupOfTwoB(password: List<Int>) = ('0'.toInt() .. '9'.toInt()).any { password.lastIndexOf(it) - password.indexOf(it) == 1 }

    override fun part1() = range.filter(::doesNotDecrease).filter(::hasSameTwo).count()
    override fun part2() = range.filter(::doesNotDecrease).filter(::hasGroupOfTwoB).count()
}