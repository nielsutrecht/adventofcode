package com.nibado.projects.advent.y2017

fun main(args : Array<String>) {
    val days = listOf(Day01, Day02, Day03, Day04)

    days.forEach {
        it.run()
    }
}