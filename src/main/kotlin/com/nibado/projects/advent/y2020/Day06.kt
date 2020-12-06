package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*

object Day06 : Day {
    private val groups = resourceStrings(2020, 6).map { it.lines() }

    override fun part1() = groups.map { g -> g.map(String::toSet).reduce { a, b -> a.union(b) }.size }.sum()
    override fun part2() = groups.map { g -> g.map(String::toSet).reduce { a, b -> a.intersect(b) }.size }.sum()
}
