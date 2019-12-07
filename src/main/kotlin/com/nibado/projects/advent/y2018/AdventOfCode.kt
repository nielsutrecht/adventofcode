package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Reflect
import com.nibado.projects.advent.Runner

object AoC2018 {
    val days = Reflect.getDays(2018).filter { it.javaClass.simpleName.matches("Day[0-9]{2}".toRegex()) }
}

fun main(args: Array<String>) {
    val day = if(args.isEmpty()) 0 else args[0].toInt()

    Runner.run(2018, AoC2018.days, day)
}