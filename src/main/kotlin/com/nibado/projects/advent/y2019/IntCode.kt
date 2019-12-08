package com.nibado.projects.advent.y2019

import com.nibado.projects.advent.toBlockingQueue
import com.nibado.projects.advent.y2019.IntCode.Opcode.*
import java.util.concurrent.BlockingQueue

class IntCode(
        val memory: MutableList<Int>,
        val input: BlockingQueue<Int>,
        val output: BlockingQueue<Int>
) {
    var terminated = false
    var ip = 0

    constructor(memory: List<Int>, input: List<Int> = emptyList(), output: List<Int> = emptyList())
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
        val op = values().find { it.op == memory[ip] % 100 } ?: throw IllegalArgumentException()
        val instruction = Instruction(op,
                (1 until op.len).map { memory[ip + it] },
                (memory[ip] / 100).toString().padStart(op.len - 1, '0').map { it == '1' }.reversed()
        )
        val result = instruction.apply(ip, memory, input, output)
        if (result == null) {
            terminated = true
        } else {
            ip = result
        }
    }

    data class Instruction(val op: Opcode, val params: List<Int>, val modes: List<Boolean>) {
        fun apply(ip: Int, memory: MutableList<Int>, input: BlockingQueue<Int>, output: BlockingQueue<Int>): Int? {
            fun get(param: Int) = if (modes[param]) params[param] else memory[params[param]]

            when (op) {
                ADD -> memory[params[2]] = get(0) + get(1)
                MUL -> memory[params[2]] = get(0) * get(1)
                SAV -> memory[params[0]] = input.take()
                OUT -> output.put(get(0))
                JIT -> if (get(0) != 0) return get(1)
                JIF -> if (get(0) == 0) return get(1)
                LT -> memory[params[2]] = if (get(0) < get(1)) 1 else 0
                EQ -> memory[params[2]] = if (get(0) == get(1)) 1 else 0
                TERM -> return null
            }

            return ip + op.len
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
        TERM(99, 1)
    }
}
