package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.Day
import com.nibado.projects.advent.collect.permutations
import com.nibado.projects.advent.resourceString
import java.util.concurrent.LinkedBlockingQueue
import kotlin.concurrent.thread

object Day07 : Day {
    private val program = resourceString(2019, 7).split(",").map { it.toLong() }

    override fun part1() = (0L..4L).toList().permutations()
            .map { run(program, it) }.maxOrNull()!!

    override fun part2() = (5L..9L).toList().permutations()
            .map { run(program, it) }.maxOrNull()!!


    private fun run(program: List<Long>, settings: List<Long>): Long {
        val queues = settings.map { i -> LinkedBlockingQueue<Long>().also { it.add(i) } }

        queues.first().put(0)

        val amps = (0..4).map { i -> IntCode(program, queues[i], queues[(i + 1) % 5]) }
        val threads = amps.map { thread { it.run() } }

        threads.forEach { it.join() }

        return amps.last().output.last()
    }
}