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
                TestInput(Day06, 5626, 46554),
                TestInput(Day07, "JNOIKSYABEQRUVWXGTZFDMHLPC", 1099),
                TestInput(Day08, 47112, 28237),
                TestInput(Day09, 375414L, 3168033673L),
                TestInput(Day10, "GJNKBZEE", 10727),
                TestInput(Day11, "20,58", "233,268,13"),
                TestInput(Day12, 1917, 1250000000991)
        ).drop(11)
    }
}