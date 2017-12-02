package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import kotlin.streams.toList

object Day02 : Day {
    override fun main(args: Array<String>) {
        val lines = resourceLines(2).map {
            it.split("\t").stream().map { it.toInt() }.toList() }.toList()

        println(lines.map(Day02::part1).sum())
        println(lines.map(Day02::part2).sum())
    }

    fun part1(line: List<Int>) : Int {
        return Math.abs(line.min()!! - line.max()!!)
    }

    fun part2(line: List<Int>) : Int {
        for(a in line) {
            line
                    .filter { a != it && a % it == 0 }
                    .forEach { return a / it }
        }

        throw RuntimeException()
    }
}