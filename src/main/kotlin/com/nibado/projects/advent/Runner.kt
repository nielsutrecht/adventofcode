package com.nibado.projects.advent

import com.nibado.projects.advent.Timer.Companion.time
import java.time.LocalDate
import java.time.format.DateTimeFormatter

object Runner {
    private const val RESULT_WIDTH = 33
    private const val TIME_WIDTH = 9
    private const val SINGLE_ITER = 1

    private val dayOfWeek = DateTimeFormatter.ofPattern("EE")
    private const val format = "%6s: %${RESULT_WIDTH}s %${RESULT_WIDTH}s %${TIME_WIDTH}s %${TIME_WIDTH}s %${TIME_WIDTH}s"

    fun run(year: Int, days: List<Day>, day: Int = 0) {
        println(format.format("Day", "Part 1", "Part 2", "Time", "P1", "P2"))
        if (day == 0) {
            days.forEach { run(year, it) }
        } else {
            if (day < 1 || day > days.size) {
                println("Day can't be less than 1 or larger than ${days.size}")
                return
            }
            (0 until SINGLE_ITER).forEach {
                run(year, days[day - 1])
            }
        }
    }

    private fun run(year: Int, day: Day) {
        val dayName = day.javaClass.simpleName.replace("Day", "").toInt()

        val date = LocalDate.of(year, 12, dayName)

        fun runPart(r: () -> Any) = try { r() } catch (e: NotImplementedError) { "TODO" }

        val (p1, dur1) = time<Any> { runPart(day::part1) }
        val (p2, dur2) = time<Any> { runPart(day::part2) }

        println(format.format("" + dayName + " " + date.format(dayOfWeek), p1, p2, formatDuration(dur1 + dur2), formatDuration(dur1), (formatDuration(dur2))))
    }
}
