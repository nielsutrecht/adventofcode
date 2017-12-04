package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day

fun main(args : Array<String>) {
    val days = listOf(Day01, Day02, Day03, Day04)

    if(args.isEmpty()) {
        days.forEach(::run)
    } else {
        val day = args[0].toInt()
        if(day < 1 || day > days.size) {
            println("Day can't be less than 1 or larger than ${days.size}")
            return
        }
        run(days[day - 1])
    }
}

fun run(day: Day) {
    val dayName = day.javaClass.simpleName.replace("Day", "")
    println("$dayName: ${day.part1()} ${day.part2()}")
}