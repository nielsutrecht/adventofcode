package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.*
import kotlin.math.absoluteValue

object Day07 : Day {
    private val values = resourceString(2021, 7).split(',').map { it.toInt() }

    override fun part1() = (values.minOrNull()!!..values.maxOrNull()!!).minOf { pos ->
        values.sumOf { (it - pos).absoluteValue }
    }

    override fun part2() = (values.minOrNull()!!..values.maxOrNull()!!).minOf { pos ->
        values.sumOf { (it - pos).absoluteValue.let { it * (it + 1) / 2 } }
    }
}