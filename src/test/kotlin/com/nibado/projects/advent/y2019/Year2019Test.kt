package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.TestInput
import com.nibado.projects.advent.YearTest

class Year2019Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
                TestInput(Day01, 3173518, 4757427),
                TestInput(Day02, 2890696, 8226)
        ).drop(0)
    }
}