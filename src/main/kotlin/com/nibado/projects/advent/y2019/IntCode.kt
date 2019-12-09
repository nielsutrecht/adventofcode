package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.toBlockingQueue
import com.nibado.projects.advent.y2019.IntCode.Opcode.*
import java.util.concurrent.BlockingQueue

class IntCode(
        val memory: MutableList<Long>,
        val input: BlockingQueue<Long>,
        val output: BlockingQueue<Long>
) {
    var terminated = false
    var ip = 0
    var rb = 0

    constructor(memory: List<Long>, input: List<Long> = emptyList(), output: List<Long> = emptyList())
            : this(memory.toMutableList(), input.toBlockingQueue(), output.toBlockingQueue())

    fun run() {
        while (!terminated) {
            step()
        }
    }

    private fun step() {
        if (terminated) {
            return
        }
        val op = values().find { it.op.toLong() == memory[ip] % 100L } ?: throw IllegalArgumentException()
        val instruction = Instruction(op,
                (1 until op.len).map { memory[ip + it] },
                (memory[ip] / 100).toString().padStart(op.len - 1, '0').map { it - '0' }.reversed()
        )
        val result = instruction.apply(ip, rb, memory, input, output)
        if (result == null) {
            terminated = true
        } else {
            ip = result.first
            rb = result.second
        }
    }

    data class Instruction(val op: Opcode, val params: List<Long>, val modes: List<Int>) {
        fun apply(ip: Int, rb: Int, memory: MutableList<Long>, input: BlockingQueue<Long>, output: BlockingQueue<Long>): Pair<Int, Int>? {
            //fun get(param: Int) = if (modes[param]) params[param] else memory[params[param]]
            var base = rb
            fun get(param: Int) = when(modes[param]) {
                0 -> memory[params[param].toInt()]
                1 -> params[param]
                2 -> memory[params[param].toInt() + base]
                else -> throw IllegalArgumentException("Invalid param $param")
            }

            fun set(param: Int, value: Long) {
                when(modes[param]) {
                    0 -> memory[params[param].toInt()] = value
                    2 -> memory[params[param].toInt() + base] = value
                }
            }

            when (op) {
                ADD -> set(2, get(0) + get(1))
                MUL -> set(2, get(0) * get(1))
                SAV -> set(0, input.take())
                OUT -> output.put(get(0))
                JIT -> if (get(0) != 0L) return get(1).toInt() to rb
                JIF -> if (get(0) == 0L) return get(1).toInt() to rb
                LT -> set(2, if (get(0) < get(1)) 1 else 0)
                EQ -> set(2, if (get(0) == get(1)) 1 else 0)
                REL -> base += get(0).toInt()
                TERM -> return null
            }

            return ip + op.len to base
        }
    }

    enum class Opcode(val op: Int, val len: Int) {
        ADD(1, 4),
        MUL(2, 4),
        SAV(3, 2),
        OUT(4, 2),
        JIT(5, 3),
        JIF(6, 3),
        LT(7, 4),
        EQ(8, 4),
        REL(9, 2),
        TERM(99, 1)
    }
}
