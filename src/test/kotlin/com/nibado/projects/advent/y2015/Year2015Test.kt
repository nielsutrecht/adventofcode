package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.TestInput
import com.nibado.projects.advent.YearTest

class Year2015Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
                TestInput(Day01, 280, 1797)
        ).drop(0)
    }
}
