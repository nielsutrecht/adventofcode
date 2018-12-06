package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Runner

object AoC2018 {
    val days = listOf(
            Day01, Day02, Day03, Day04, Day05,
            Day06)
}

fun main(args: Array<String>) {
    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(2018, AoC2018.days, day)
}