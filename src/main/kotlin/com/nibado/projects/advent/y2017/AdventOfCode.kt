package com.nibado.projects.advent.y2017

fun main(args : Array<String>) {
    val days = listOf(Day01)

    days.forEach {
        it.run()
    }
}