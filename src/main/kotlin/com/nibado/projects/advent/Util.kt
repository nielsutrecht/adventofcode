package com.nibado.projects.advent

import java.io.InputStream
import kotlin.streams.toList

private val HEX_CHARS = "0123456789abcdef".toCharArray()
const val CURRENT_YEAR = 2017

fun resource(day: Int) = resource(CURRENT_YEAR, day)

fun resource(year: Int, day: Int) : InputStream {
    val name = String.format("/%d/day%02d.txt", year, day)

    return String::class.java.getResourceAsStream(name) ?: throw IllegalArgumentException("No resource with name " + name)
}

fun resourceString(day: Int) : String {
    return resource(day).bufferedReader().use { it.readText() }
}

fun resourceString(year: Int, day: Int) : String {
    return resource(year, day).bufferedReader().use { it.readText() }
}

fun resourceLines(year: Int, day: Int) : List<String> {
    return resource(year, day).bufferedReader().lines().toList()
}

fun resourceLines(day: Int) : List<String> {
    return resource(day).bufferedReader().lines().toList()
}

fun resourceRegex(day: Int, regex: Regex) = resourceRegex(CURRENT_YEAR, day, regex)

fun resourceRegex(year: Int, day: Int, regex: Regex) : List<List<String>> {
    val lines = resourceLines(year, day)

    val misMatch = lines.filter { !regex.matches(it) }

    if(!misMatch.isEmpty()) {
        misMatch.forEach { println(it) }
        println("${misMatch.size} lines don't match regex ${regex.pattern}")
    }

    return lines.map { regex.matchEntire(it)!!.groupValues }.toList()
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

fun permutations(list: List<Any>) : List<List<Any>> {
    val permutations: MutableList<List<Any>> = mutableListOf()

    permutate(list, listOf(), permutations)

    return permutations
}

private fun permutate(head: List<Any>, tail: List<Any>, permutations: MutableList<List<Any>>) {
    if(head.isEmpty()) {
        permutations += tail
        return
    }

    for(i in 0 until head.size) {
        val newHead = head.filterIndexed({index, _ -> index != i}).toList()
        val newTail = tail + head[i]

        permutate(newHead, newTail, permutations)
    }
}

fun factorial(num: Int) = (1..num).fold(1, {a, b -> a * b})

fun slices(line: String, len: Int) = (0 .. line.length - len)
        .map { line.slice(it until it + len) }

fun testRegex(regex: Regex, lines: List<String>) {
    val mismatching = lines.count { !regex.matches(it) }
    lines.filter { !regex.matches(it) }.forEach { println(it) }

    println("${lines.size} lines, matching: ${lines.size - mismatching}, not matching: $mismatching")
}

fun ByteArray.toHex() : String{
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

fun main(args: Array<String>) {

}