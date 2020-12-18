
package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import java.util.*


object Day18 : Day {

    private val values = resourceLines(2020, 18)
    private val operators = setOf("+", "-", "*", "/")

    private fun precedence(token: String, other: String) : Boolean = true

    override fun part1() = TODO()
    override fun part2() = TODO()

    fun shunt(input: String) : List<String> {
        val output = mutableListOf<String>()
        val operatorStack = Stack<String>()

        tokens(input).forEach { token ->
            if(token.isInt()) {
                output += token
            } else if(token in operators) {
                while(!operatorStack.empty() && precedence(token,operatorStack.peek()) && operatorStack.peek() != "(") {
                    output += operatorStack.pop()
                }
                operatorStack += token
            }
            else if(token == "(") {
                operatorStack += token
            } else if(token == ")") {
                while(!operatorStack.empty() && operatorStack.peek() != "(") {
                    output += operatorStack.pop()
                }
                if(operatorStack.peek() == "(") {
                    operatorStack.pop()
                }
            }
        }
        while(!operatorStack.empty()) {
            output += operatorStack.pop()
        }

        return output
    }

    fun tokens(input: String) = sequence<String> {
        var i = 0
        while(i < input.length) {
            if(input[i].toString() in operators) {
                yield(input[i].toString())
                i++
            } else if(input[i].isDigit()){
                var j = i
                while(j < input.length && input[j].isDigit()) {
                    j++
                }
                yield(input.substring(i until j))
                i = j + 1
            } else {
                i++
            }
        }
    }

    fun eval(expr: List<String>) : Long {
        val stack = Stack<Long>()
        println(expr)
        expr.forEach {
            if(it.isInt()) {
                stack += it.toLong()
            } else {
                val a = stack.pop()
                val b = stack.pop()
                stack += when(it) {
                    "+" -> a + b
                    "-" -> a - b
                    "/" -> a / b
                    "*" -> a * b
                    else -> throw IllegalArgumentException(it)
                }
            }
        }

        return stack.pop()
    }

}

fun main() {
    println(Day18.eval(Day18.shunt("2 * 3 + (4 * 5)").toList()))

}
