package com.nibado.projects.advent

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Runner {
    private const val RESULT_WIDTH = 33
    private const val TIME_WIDTH = 9

    private val dayOfWeek = DateTimeFormatter.ofPattern("EEE")
    private val format = "%8s: %${RESULT_WIDTH}s %${RESULT_WIDTH}s %${TIME_WIDTH}s %${TIME_WIDTH}s %${TIME_WIDTH}s"

    fun run(year: Int, days: List<Day>, day: Int = 0) {
        println(format.format("Day", "Part 1", "Part 2", "Time", "P1", "P2"))
        if (day == 0) {
            days.forEach { run(year, it) }
        } else {
            if (day < 1 || day > days.size) {
                println("Day can't be less than 1 or larger than ${days.size}")
                return
            }
            run(year, days[day - 1])
        }
    }

    private fun run(year: Int, day: Day) {
        val dayName = day.javaClass.simpleName.replace("Day", "").toInt()

        val date = LocalDate.of(year, 12, dayName)

        val start1 = System.currentTimeMillis()
        val p1 = day.part1()
        val dur1 = System.currentTimeMillis() - start1
        val start2 = System.currentTimeMillis()
        val p2 = day.part2()
        val dur2 = System.currentTimeMillis() - start2

        println(format.format("" + dayName + " " + date.format(dayOfWeek), p1, p2, formatDuration(System.currentTimeMillis() - start1), formatDuration(dur1), (formatDuration(dur2))))
    }
}