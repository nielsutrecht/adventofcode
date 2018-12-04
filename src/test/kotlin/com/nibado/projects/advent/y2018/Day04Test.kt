package com.nibado.projects.advent.y2018

import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class Day04Test {

    @Test
    fun part1() {
        assertThat(Day04.part1()).isEqualTo("19025")
    }

    @Test
    fun part2() {
        assertThat(Day04.part2()).isEqualTo("23776")
    }
}