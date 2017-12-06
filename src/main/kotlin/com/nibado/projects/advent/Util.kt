package com.nibado.projects.advent

import java.io.InputStream
import kotlin.streams.toList

fun resource(day: Int) : InputStream {
    val name = String.format("/2017/day%02d.txt", day)

    val ins = String::class.java.getResourceAsStream(name)

    if(ins == null) {
        throw IllegalArgumentException("No resource with name " + name)
    }

    return ins;
}

fun resourceString(day: Int) : String {
    return resource(day).bufferedReader().use { it.readText() }
}

fun resourceLines(day: Int) : List<String> {
    return resource(day).bufferedReader().lines().toList()
}

fun stringToDigits(s: String): List<Int> {
    if(!s.matches(Regex("[0-9]+"))) {
        throw IllegalArgumentException("s does not match [0-9]+")
    }

    return s.toCharArray().map { c -> c - '0' }
}

fun join(numbers: List<Int>): String {
    return numbers.joinToString(",")
}
