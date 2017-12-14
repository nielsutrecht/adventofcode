package com.nibado.projects.advent

import java.io.InputStream
import java.time.Duration
import kotlin.streams.toList

private val HEX_CHARS = "0123456789abcdef".toCharArray()
const val CURRENT_YEAR = 2017

fun resource(day: Int) = resource(CURRENT_YEAR, day)

fun resource(year: Int, day: Int): InputStream {
    val name = String.format("/%d/day%02d.txt", year, day)

    return String::class.java.getResourceAsStream(name) ?: throw IllegalArgumentException("No resource with name " + name)
}

fun resourceString(day: Int): String {
    return resource(day).bufferedReader().use { it.readText() }
}

fun resourceString(year: Int, day: Int): String {
    return resource(year, day).bufferedReader().use { it.readText() }
}

fun resourceLines(year: Int, day: Int): List<String> {
    return resource(year, day).bufferedReader().lines().toList()
}

fun resourceLines(day: Int): List<String> {
    return resource(day).bufferedReader().lines().toList()
}

fun resourceRegex(day: Int, regex: Regex) = resourceRegex(CURRENT_YEAR, day, regex)

fun resourceRegex(year: Int, day: Int, regex: Regex): List<List<String>> {
    val lines = resourceLines(year, day)

    val misMatch = lines.filter { !regex.matches(it) }

    if (!misMatch.isEmpty()) {
        misMatch.forEach { println(it) }
        println("${misMatch.size} lines don't match regex ${regex.pattern}")
    }

    return lines.map { regex.matchEntire(it)!!.groupValues }.toList()
}

fun stringToDigits(s: String): List<Int> {
    if (!s.matches(Regex("[0-9]+"))) {
        throw IllegalArgumentException("s does not match [0-9]+")
    }

    return s.toCharArray().map { c -> c - '0' }
}

fun join(numbers: List<Int>): String {
    return numbers.joinToString(",")
}

fun permutations(list: List<Any>): List<List<Any>> {
    val permutations: MutableList<List<Any>> = mutableListOf()

    permutate(list, listOf(), permutations)

    return permutations
}

private fun permutate(head: List<Any>, tail: List<Any>, permutations: MutableList<List<Any>>) {
    if (head.isEmpty()) {
        permutations += tail
        return
    }

    for (i in 0 until head.size) {
        val newHead = head.filterIndexed({ index, _ -> index != i }).toList()
        val newTail = tail + head[i]

        permutate(newHead, newTail, permutations)
    }
}

fun factorial(num: Int) = (1..num).fold(1, { a, b -> a * b })

fun slices(line: String, len: Int) = (0..line.length - len)
        .map { line.slice(it until it + len) }

fun testRegex(regex: Regex, lines: List<String>) {
    val mismatching = lines.count { !regex.matches(it) }
    lines.filter { !regex.matches(it) }.forEach { println(it) }

    println("${lines.size} lines, matching: ${lines.size - mismatching}, not matching: $mismatching")
}

fun ByteArray.toHex(): String {
    val result = StringBuffer()

    forEach {
        val octet = it.toInt()
        val firstIndex = (octet and 0xF0).ushr(4)
        val secondIndex = octet and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }

    return result.toString()
}

fun List<Int>.toHex(): String {
    val result = StringBuffer()

    forEach {
        if(it > 255) {
            throw IllegalArgumentException("$it larger than 255")
        }
        val firstIndex = (it and 0xF0).ushr(4)
        val secondIndex = it and 0x0F
        result.append(HEX_CHARS[firstIndex])
        result.append(HEX_CHARS[secondIndex])
    }

    return result.toString()
}

fun String.isInt() = matches(Regex("-?[0-9]+"))

fun rotate(list: List<Any>, amount: Int): List<Any> {
    val amt = amount % list.size

    return list.subList(amt, list.size) + list.subList(0, amt)
}

fun formatDuration(ms: Long): String {
    val d = Duration.ofMillis(ms)
    if (ms > 60000) {
        return String.format("%s m %s s", d.toMinutes(), d.minusMinutes(d.toMinutes()).seconds)
    } else if (ms > 10000) {
        return String.format("%s s", d.seconds)
    } else if (ms > 1000) {
        return String.format("%.2f s", ms / 1000.0)
    } else {
        return String.format("%s ms", ms)
    }
}

fun reverse(list: MutableList<Int>, index: Int, length: Int) {
    val indices = (index until index + length).map { it % list.size }
    val subList = list.slice(indices).reversed()
    indices.forEachIndexed { i, v -> list[v] = subList[i] }
}

fun xor(list: List<Int>) = list.fold(0, { a, b -> a xor  b })

fun toBinary(hex: String) = hex.map { it.toString().toInt(16).toString(2).padStart(4, '0') }.joinToString("")

fun List<Any>.parallelMap(threads: Int, func: (a: Any) -> Any): List<Any> {

    return this.map { func(it) }
}

fun main(args: Array<String>) {
    (0 .. 32).map { it.toString() }.parallelMap(4, {"" + it + it}).forEach { println(it) }
}

