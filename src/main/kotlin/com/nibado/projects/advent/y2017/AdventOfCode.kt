package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Runner

fun main(args: Array<String>) {
    val days = listOf(Day01, Day02, Day03, Day04, Day05, Day06, Day07, Day08, Day09, Day10, Day11, Day12, Day13)

    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(days, day)
}