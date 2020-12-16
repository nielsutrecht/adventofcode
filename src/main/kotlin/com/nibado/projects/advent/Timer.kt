package com.nibado.projects.advent

import java.time.Duration

class Timer private constructor(private val duration: Long) {
    private var start = System.currentTimeMillis()

    fun tick(runnable: () -> Unit) {
        val current = System.currentTimeMillis()
        if(current - start > duration) {
            runnable()
            start = current
        }
    }

    companion object {
        fun of(duration: Duration) = Timer(duration.toMillis())
        fun ofSeconds(seconds: Int) = Timer(seconds * 1000L)
        fun ofMillis(milliseconds: Long) = Timer(milliseconds)

        fun time(runnable: () -> Unit) : Long {
            val start = System.currentTimeMillis()
            run(runnable)
            val stop = System.currentTimeMillis()

            return stop - start
        }

        inline fun <reified T> time(runnable: () -> T) : Pair<T, Long> {
            val start = System.currentTimeMillis()
            val result = run(runnable)
            val stop = System.currentTimeMillis()

            return result to stop - start
        }

        fun print(runnable: () -> Unit) {
            val start = System.currentTimeMillis()
            run(runnable)
            val stop = System.currentTimeMillis()

            val seconds = (stop - start) / 1000L

            println("$seconds.${(stop - start) % 1000L}s")
        }
    }
}
