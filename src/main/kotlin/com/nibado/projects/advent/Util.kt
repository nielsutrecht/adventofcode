package com.nibado.projects.advent

import java.io.InputStream
import java.time.Duration
import java.util.concurrent.LinkedBlockingQueue
import java.util.stream.Collectors

private val HEX_CHARS = "0123456789abcdef".toCharArray()

fun resource(year: Int, day: Int): InputStream {
    val name = String.format("/%d/day%02d.txt", year, day)

    return String::class.java.getResourceAsStream(name)
            ?: throw IllegalArgumentException("No resource with name " + name)
}

fun resourceString(year: Int, day: Int): String {
    return resource(year, day).bufferedReader().use { it.readText() }
}

fun resourceStrings(year: Int, day: Int, delimiter: String = "\n\n"): List<String>
    = resourceString(year, day).split(delimiter).map { it.trim() }

fun resourceLines(year: Int, day: Int): List<String> {
    return resource(year, day).bufferedReader().readLines()
}

fun resourceRegex(year: Int, day: Int, regex: String): List<List<String>> =
        resourceRegex(year, day, regex.toRegex())

fun resourceRegex(year: Int, day: Int, regex: Regex): List<List<String>> {
    val lines = resourceLines(year, day)

    val misMatch = lines.filter { !regex.matches(it) }

    if (!misMatch.isEmpty()) {
        misMatch.forEach { println(it) }
        println("${misMatch.size} lines don't match regex ${regex.pattern}")
    }

    return lines.map { regex.matchEntire(it)!!.groupValues }.toList()
}

fun resourceRegex(year: Int, day: Int, regex: Map<String, Regex>) = resourceLines(year, day).map { matchRegex(it, regex) }

private fun matchRegex(line: String, regex: Map<String, Regex>): Pair<String, List<String>> {
    val matches = regex.entries.map { it.key to it.value.matchEntire(line) }.filter { it.second != null }
    if (matches.size != 1) {
        throw RuntimeException("Wrong number of matchers for '$line': ${matches.size}")
    }

    return matches.map { it.first to it.second!!.groupValues }.first()
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
        if (it > 255) {
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

fun <T> rotateLeft(list: List<T>, amount: Int): List<T> {
    val amt = amount % list.size

    return list.subList(amt, list.size) + list.subList(0, amt)
}

fun <T> rotateRight(list: List<T>, amount: Int): List<T> {
    val amt = amount % list.size

    return list.subList(list.size - amt, list.size) + list.subList(0, list.size - amt)
}

fun <T> swap(list: List<T>, a: Int, b: Int): List<T> {
    list.indices.map { if (it == a) b else if (it == b) a else it }

    return list.slice(list.indices.map { if (it == a) b else if (it == b) a else it })
}

fun <T> swapByValue(list: List<T>, a: T, b: T) = swap(list, list.indexOf(a), list.indexOf(b))

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

fun xor(list: List<Int>) = list.fold(0, { a, b -> a xor b })

fun toBinary(hex: String) = hex.map { it.toString().toInt(16).toString(2).padStart(4, '0') }.joinToString("")

fun Int.toBinary() = toString(2).padStart(32, '0')

fun List<Any>.parallelMap(threads: Int, func: (a: Any) -> Any): List<Any> {

    return this.map { func(it) }
}

fun <T> List<T>.toBlockingQueue() = LinkedBlockingQueue<T>().also { q -> forEach { i -> q.put(i) } }

fun combine(ranges: List<LongRange>): List<LongRange> {
    val list = mutableListOf<LongRange>()

    ranges.forEach { r ->
        val others: List<LongRange> = list.filter { overlap(r, it) }

        list.removeAll(others)
        val combined = others + listOf(r)
        list += combined.map { it.first }.min()!!..combined.map { it.last }.max()!!
    }

    return list.sortedBy { it.first }
}

fun overlap(a: LongRange, b: LongRange): Boolean {
    return a.contains(b.first)
            || a.contains(b.last)
            || b.contains(a.first)
            || b.contains(a.last) || a.last + 1 == b.first || b.last + 1 == a.first
}

fun IntRange.center() : Int = this.first + (this.last - this.first) / 2
fun IntRange.split() : Pair<IntRange, IntRange> =
        this.first .. this.center() to this.center() + 1 .. this.last

fun main(args: Array<String>) {
    val list = (0..5).toList()

    println(rotateLeft(list, 20))
    println(rotateRight(list, 20))
}

