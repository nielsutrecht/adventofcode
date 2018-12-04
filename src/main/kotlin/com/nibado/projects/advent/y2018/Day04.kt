package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.resourceLines
import java.time.LocalDateTime

object Day04 : Day {
    private val regex = "\\[([0-9]{4})-([0-9]{2})-([0-9]{2}) ([0-9]{2}):([0-9]{2})] (Guard #([0-9]+) begins shift|falls asleep|wakes up)".toRegex()
    private val instructions = resourceLines(2018, 4).mapNotNull { regex.matchEntire(it)?.groupValues?.drop(1) }
            .map {
                val time = LocalDateTime.of(it[0].toInt(), it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt())
                val guard = if(it[5].startsWith("Guard")) { it[6].toInt() } else null

                Event(time, guard, it[5])
            }
            .sortedBy { it.time }

    private val map: Map<Int, Map<Int,Int>> by lazy {
        val map = mutableMapOf<Int, MutableMap<Int, Int>>()

        var guard = 0
        var asleep: LocalDateTime? = null

        instructions.forEach {
            when {
                it.guard != null -> guard = it.guard
                it.inst.startsWith("falls") -> asleep = it.time
                it.inst.startsWith("wakes") -> {
                    val minutes = (asleep!!.minute until it.time.minute).map { m -> asleep!!.withMinute(m) }

                    minutes.forEach { m ->
                        map.computeIfAbsent(guard) { mutableMapOf() }.compute(m.minute) { _, v -> v?.plus(1) ?: 1}
                    }
                }
            }
        }
        map
    }

    override fun part1() : String {
        val guard = map.maxBy { it.value.values.sum() }!!
        val minute = guard.value.entries.maxBy { it.value }!!.key

        return (guard.key * minute).toString()
    }

    override fun part2() : String {
        val max = map.map {
            it.key to it.value.maxBy { m -> m.value }!!
        }.maxBy { it.second.value } ?: throw IllegalArgumentException()

        return (max.first * max.second.key).toString()
    }

    data class Event(val time: LocalDateTime, val guard: Int?, val inst: String)
}