package com.nibado.projects.advent

import java.time.Duration

class Benchmark(
        private val days: List<Day>,
        warmup: Duration = Duration.ofSeconds(3),
        benchmark: Duration = Duration.ofSeconds(5)) {

    constructor(day: Day,
                warmup: Duration = Duration.ofSeconds(3),
                benchmark: Duration = Duration.ofSeconds(5)) : this(listOf(day), warmup, benchmark)


    private val warmupTime = warmup.toMillis()
    private val benchmarkTime = benchmark.toMillis()

    fun run() {
        println("Running ${days.size} days.")
        days.forEach(::run)
    }

    private fun run (day: Day) {
        print(day.javaClass.simpleName)


        doUntil(System.currentTimeMillis() + warmupTime) { day.part1() }
        print(".")

        doUntil(System.currentTimeMillis() + warmupTime) { day.part2() }
        print(".")

        val bench1 = doUntil(System.currentTimeMillis() + benchmarkTime) { day.part1() }
        print(".")

        val bench2 = doUntil(System.currentTimeMillis() + benchmarkTime) { day.part2() }
        print(".")

        println(format(bench1) + " " + format(bench2))
    }

    private fun doUntil(until: Long, r: () -> Unit) : Pair<Long, Int> {
        val start = System.currentTimeMillis()
        var iter = 0
        while(System.currentTimeMillis() < until) {
            r.invoke()
            iter++
        }

        return (System.currentTimeMillis() - start) to iter
    }

    private fun format(cycle: Pair<Long, Int>) = format(cycle.first.toDouble() / cycle.second.toDouble())
    private fun format(ms: Double)
        = when {
            ms > 1000.0 -> "%.1f s".format(ms / 1000.0)
            else -> "%.1f ms".format(ms)
        }
}