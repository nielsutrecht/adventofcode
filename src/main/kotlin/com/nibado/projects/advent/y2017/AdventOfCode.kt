package com.nibado.projects.advent.y2017

import com.nibado.projects.advent.Day
import java.lang.System.currentTimeMillis

fun main(args: Array<String>) {
    val days = listOf(Day01, Day02, Day03, Day04, Day05, Day06, Day07, Day08)

    if (args.isEmpty()) {
        println("%s: %8s %8s %7s %7s %7s".format("Day", "Part 1", "Part 2", "Time", "P1", "P2"))
        days.forEach(::run)
    } else {
        val day = args[0].toInt()
        if (day < 1 || day > days.size) {
            println("Day can't be less than 1 or larger than ${days.size}")
            return
        }
        run(days[day - 1])
    }
}

fun run(day: Day) {
    val dayName = day.javaClass.simpleName.replace("Day", "")

    val start1 = currentTimeMillis()
    val p1 = day.part1()
    val dur1 = currentTimeMillis() - start1
    val start2 = currentTimeMillis()
    val p2 = day.part2()
    val dur2 = currentTimeMillis() - start2

    println(" %s: %8s %8s %4s ms %4s ms %4s ms".format(dayName, p1, p2, currentTimeMillis() - start1, dur1, dur2))
}