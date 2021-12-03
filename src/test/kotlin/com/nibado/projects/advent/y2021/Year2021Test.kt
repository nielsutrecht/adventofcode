package com.nibado.projects.advent.y2021

import com.nibado.projects.advent.TestInput
import com.nibado.projects.advent.YearTest

class Year2021Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
            TestInput(Day01, 1624, 1653),
            TestInput(Day02, 1989014L, 2006917119L),
            TestInput(Day03, 1997414L, 1032597L)
        ).drop(0)
    }
}
