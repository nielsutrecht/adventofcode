package com.nibado.projects.advent

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory

abstract class YearTest(private val input: List<TestInput>) {
    @TestFactory
    fun tests() = input.flatMap(::map)

    private fun map(ti: TestInput): List<DynamicTest> {
        val year = ti.day.javaClass.`package`.name.removePrefix("com.nibado.projects.advent.y").toInt()
        val day = ti.day.javaClass.simpleName.removePrefix("Day").toInt()

        return listOf(
                DynamicTest.dynamicTest("$year $day part 1") {
                    if (ti.expected1 != null) {
                        Assertions.assertThat(ti.day.part1()).isEqualTo(ti.expected1)
                    } else {
                        println(ti.day.part1())
                    }
                },
                DynamicTest.dynamicTest("$year $day part 2") {
                    if (ti.expected2 != null) {
                        Assertions.assertThat(ti.day.part2()).isEqualTo(ti.expected2)
                    } else {
                        println(ti.day.part2())
                    }
                }
        )
    }
}

data class TestInput(val day: Day, val expected1: Any? = null, val expected2: Any? = null)