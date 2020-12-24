package com.nibado.projects.advent

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicNode
import org.junit.jupiter.api.DynamicTest
import org.junit.jupiter.api.TestFactory
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import kotlin.system.measureTimeMillis

abstract class YearTest(private val input: List<TestInput>) {
    @TestFactory
    @Execution(ExecutionMode.CONCURRENT)
    fun tests() = input.flatMap(::map)

    private fun map(ti: TestInput): List<DynamicTest> {
        val year = ti.day.javaClass.`package`.name.removePrefix("com.nibado.projects.advent.y").toInt()
        val day = ti.day.javaClass.simpleName.removePrefix("Day").toInt()

        return listOf(
                DynamicTest.dynamicTest("$year $day part 1") {
                    println(Thread.currentThread().name)
                    if (ti.expected1 != null) {
                        val time = measureTimeMillis { Assertions.assertThat(ti.day.part1()).isEqualTo(ti.expected1) }
                        println("$year $day p1: $time ms")
                    } else {
                        println(ti.day.part1())
                    }
                },
                DynamicTest.dynamicTest("$year $day part 2") {
                    if (ti.expected2 != null) {
                        val time = measureTimeMillis { Assertions.assertThat(ti.day.part2()).isEqualTo(ti.expected2) }
                        println("$year $day p2: $time ms")
                    } else {
                        println(ti.day.part2())
                    }
                }
        )
    }
}

data class TestInput(val day: Day, val expected1: Any? = null, val expected2: Any? = null)