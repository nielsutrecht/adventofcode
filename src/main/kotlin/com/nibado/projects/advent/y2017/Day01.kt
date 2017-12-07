package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString
import com.nibado.projects.advent.stringToDigits

object Day01 : Day {
    val digits = stringToDigits(resourceString(1))

    override fun part1() = sum(digits, { i -> (i + 1) % digits.size }).toString()
    override fun part2() = sum(digits, { i -> (i + digits.size / 2) % digits.size }).toString()

    fun sum(digits: List<Int>, index: (Int) -> Int) = digits.indices
                .filter { digits[it] == digits[index.invoke(it)] }
                .sumBy { digits[it] }
}