package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines

object Day01 : Day {
    private val values = resourceLines(2020, 1).map { it.toInt() }

    override fun part1() = values
            .flatMap { a -> values.map {b -> listOf(a, b) } }
            .first { it.sum() == 2020 }
            .let { (a, b) -> a * b }

    override fun part2() = values
            .flatMap { a ->
                values.filterNot { b -> a + b > 2020 }.flatMap { b ->
                    values.map {c -> listOf(a, b, c) } } }
            .first { it.sum() == 2020 }
            .let { (a, b, c) -> a * b * c }
}
