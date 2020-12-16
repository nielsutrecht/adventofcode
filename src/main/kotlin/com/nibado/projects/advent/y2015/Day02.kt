package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.*

object Day02 : Day {
    private val values = resourceLines(2015, 2)
            .map { line -> line.split("x").map(String::toInt).sorted() }

    private fun size(dim: List<Int>) = dim
            .let { (l, w, h) -> listOf(l * w, w * h, h * l).let { it.map { it * 2 }.sum() + it.min()!! } }

    private fun length(dim: List<Int>) = dim.let { (a, b, c) -> a + a + b + b + a * b * c }

    override fun part1() = values.map(::size).sum()
    override fun part2() = values.map(::length).sum()
}
