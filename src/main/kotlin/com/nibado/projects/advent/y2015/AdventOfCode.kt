package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.Reflect
import com.nibado.projects.advent.Runner

object AoC2015 {
    val days = Reflect.getDays(2015)
}

fun main(args: Array<String>) {
    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(2015, AoC2015.days, day)
}