package com.nibado.projects.advent

object Runner {
    private const val RESULT_WIDTH = 12
    private const val TIME_WIDTH = 9

    private val format = "%3s: %${RESULT_WIDTH}s %${RESULT_WIDTH}s %${TIME_WIDTH}s %${TIME_WIDTH}s %${TIME_WIDTH}s"

    fun run(days: List<Day>, day: Int = 0) {
        println(format.format("Day", "Part 1", "Part 2", "Time", "P1", "P2"))
        if (day == 0) {
            days.forEach(Runner::run)
        } else {
            if (day < 1 || day > days.size) {
                println("Day can't be less than 1 or larger than ${days.size}")
                return
            }
            run(days[day - 1])
        }
    }

    private fun run(day: Day) {
        val dayName = day.javaClass.simpleName.replace("Day", "")

        val start1 = System.currentTimeMillis()
        val p1 = day.part1()
        val dur1 = System.currentTimeMillis() - start1
        val start2 = System.currentTimeMillis()
        val p2 = day.part2()
        val dur2 = System.currentTimeMillis() - start2

        println(format.format(dayName, p1, p2, formatDuration(System.currentTimeMillis() - start1), formatDuration(dur1), (formatDuration(dur2))))
    }
}