package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Runner

fun main(args: Array<String>) {
    val days = listOf(Day01, Day02, Day03, Day04, Day05, Day06, Day07, Day08, Day09)

    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(days, day)
}