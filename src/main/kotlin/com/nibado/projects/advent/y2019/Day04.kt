package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day

object Day04 : Day {
    private val range = (108457 .. 562041).map { it.toString().map {c -> c.toInt() } }

    private fun doesNotDecrease(password: List<Int>) = (0..4).none { password[it] > password[it + 1] }
    private fun hasSameTwo(password: List<Int>) = (0..4).any { password[it] == password[it + 1] }
    private fun hasGroupOfTwo(password: List<Int>) = groups(password).contains(2)

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

    override fun part1() = range.filter(::doesNotDecrease).filter(::hasSameTwo).count()
    override fun part2() = range.filter(::doesNotDecrease).filter(::hasGroupOfTwo).count()
}