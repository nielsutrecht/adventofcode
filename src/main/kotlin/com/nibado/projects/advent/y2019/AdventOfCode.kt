package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Reflect
import com.nibado.projects.advent.Runner

object AoC2019 {
    val days = Reflect.getDays(2019)
}

fun main(args: Array<String>) {
    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(2019, AoC2019.days, day)
}