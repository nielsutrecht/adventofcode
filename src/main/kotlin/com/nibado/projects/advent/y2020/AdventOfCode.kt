package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.Reflect
import com.nibado.projects.advent.Runner
import com.nibado.projects.advent.resourceString

object AoC2020 {
    val days = Reflect.getDays(2020)
}

fun main(args: Array<String>) {
    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(2020, AoC2020.days, day)
}
