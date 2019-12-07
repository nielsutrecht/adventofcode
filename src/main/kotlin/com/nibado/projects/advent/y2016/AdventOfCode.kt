package com.nibado.projects.advent.y2016

import com.nibado.projects.advent.Reflect
import com.nibado.projects.advent.Runner

fun main(args: Array<String>) {
    val days = Reflect.getDays(2016)

    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(2016, days, day)
}