package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.Reflect
import com.nibado.projects.advent.Runner
import com.nibado.projects.advent.resourceString

object AoC2021 {
    val days = Reflect.getDays(2021)
}

fun main(args: Array<String>) {
    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(2021, AoC2021.days, day)
}
