package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day

object Day04 : Day {
    private val range = (108457 .. 562041).map { it to it.toString().map {c -> c.toInt() } }

    private fun inRange(password: Pair<Int, List<Int>>) = password.first in 100000..999999
    private fun doesNotDecrease(password: Pair<Int, List<Int>>) = (0..4).none { password.second[it] > password.second[it + 1] }
    private fun hasSameTwo(password: Pair<Int, List<Int>>) = (0..4).any { password.second[it] == password.second[it + 1] }
    private fun hasGroupOfTwo(password: Pair<Int, List<Int>>) = groups(password.second).contains(2)

    private fun groups(digits: List<Int>) : Set<Int> {
        var current = digits.first()
        var count = 1
        val counts = mutableListOf<Int>()
        for(i in (1 until digits.size)) {
            if(current == digits[i]) {
                count++
            } else {
                counts += count
                count = 1
            }
            current = digits[i]
        }
        counts += count

        return counts.toSet()
    }

    override fun part1() = range.filter(::inRange).filter(::doesNotDecrease).filter(::hasSameTwo).count()
    override fun part2() = range.filter(::inRange).filter(::doesNotDecrease).filter(::hasGroupOfTwo).count()
}