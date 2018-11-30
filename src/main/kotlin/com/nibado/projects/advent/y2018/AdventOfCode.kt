package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Runner

fun main(args: Array<String>) {
    val days = listOf(
            Day01)

    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(2018, days, day)
}