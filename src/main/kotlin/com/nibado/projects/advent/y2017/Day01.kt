package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceString
import com.nibado.projects.advent.stringToDigits

object Day01 : Day {
    override fun main(args: Array<String>) {
        val digits = stringToDigits(resourceString(1))

        sum(digits, { i -> (i + 1) % digits.size })
        sum(digits, { i -> (i + digits.size / 2) % digits.size })
    }

    fun sum(digits: List<Int>, index: (Int) -> Int) {
        val sum = digits.indices
                .filter { digits[it] == digits[index.invoke(it)] }
                .sumBy { digits[it] }

        println(sum)
    }
}