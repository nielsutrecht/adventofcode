package com.nibado.projects.advent

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

abstract class DayTest(private val day: Day, private val part1: Any? = null, private val part2: Any? = null) {
    @Test
    fun part1() {
        if(part1 != null) {
            assertThat(day.part1()).isEqualTo(part1)
        } else {
            println(day.part1())
        }
    }

    @Test
    fun part2() {
        if(part2 != null) {
            assertThat(day.part2()).isEqualTo(part2)
        } else {
            println(day.part2())
        }
    }
}