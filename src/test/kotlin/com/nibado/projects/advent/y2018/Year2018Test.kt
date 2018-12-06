package com.nibado.projects.advent.y2018

import com.nibado.projects.advent.TestInput
import com.nibado.projects.advent.YearTest

class Year2018Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
                TestInput(Day01, 406, 312),
                TestInput(Day02, 7134, "kbqwtcvzhmhpoelrnaxydifyb"),
                TestInput(Day03, 116920, 382),
                TestInput(Day04, 19025, 23776),
                TestInput(Day05, 10450, 4624),
                TestInput(Day06, 5626, 46554)
        )
    }
}