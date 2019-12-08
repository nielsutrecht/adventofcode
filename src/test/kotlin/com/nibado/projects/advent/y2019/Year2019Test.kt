package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.TestInput
import com.nibado.projects.advent.YearTest

class Year2019Test : YearTest(input()) {
    companion object {
        fun input() = listOf(
                TestInput(Day01, 3173518, 4757427),
                TestInput(Day02, 2890696, 8226),
                TestInput(Day03, 316, 16368),
                TestInput(Day04, 2779, 1972),
                TestInput(Day05, 9219874, 5893654),
                TestInput(Day06, 117672, 277),
                TestInput(Day07, 87138, 17279674),
                TestInput(Day08, 1330, DAY08_P2)
        ).drop(0)

        const val DAY08_P2 =
"""XXXX  XX  X  X XXXX XXXX 
X    X  X X  X X    X    
XXX  X  X XXXX XXX  XXX  
X    XXXX X  X X    X    
X    X  X X  X X    X    
X    X  X X  X XXXX X    """
    }
}