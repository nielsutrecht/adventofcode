package com.nibado.projects.advent.y2015

import com.nibado.projects.advent.TestInput
import com.nibado.projects.advent.YearTest

class Year2015Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
                TestInput(Day01, 280, 1797),
                TestInput(Day02, 1598415, 3812909),
                TestInput(Day03, 2565, 2639),
                TestInput(Day04, 346386, 9958218),
                TestInput(Day05, 238, 69)
        ).drop(0)
    }
}
