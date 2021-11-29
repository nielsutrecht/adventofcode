package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.TestInput
import com.nibado.projects.advent.YearTest

class Year2021Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
                TestInput(Day01, 0, 0),
        ).drop(0)
    }
}
