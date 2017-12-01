package com.nibado.projects.advent


fun resource(day: Int) : String {
    val name = String.format("/2017/day%02d.txt", day)

    val ins = String::class.java.getResourceAsStream(name)

    return ins.bufferedReader().use { it.readText() }
}

fun stringToDigits(s: String): List<Int> {
    if(!s.matches(Regex("[0-9]+"))) {
        throw IllegalArgumentException("s does not match [0-9]+")
    }

    return s.toCharArray().map { c -> c - '0' }
}
