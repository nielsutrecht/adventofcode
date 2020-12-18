package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import java.lang.StringBuilder
import java.util.*


object Day18 : Day {
    private val values = resourceLines(2020, 18)

    override fun part1() = values.map { parse(it) { 1 } }.sum()
    override fun part2() = values.map { parse(it) { t -> if (t == "+") 2 else 1 } }.sum()

    private fun parse(input: String, precedence: (String) -> Int) : Long {
        val builder = StringBuilder(input)

        while(true) {
            val braceIndex = builder.indexOf('(')
            if (braceIndex >= 0) {
                var c = 1
                var otherBrace = braceIndex
                while (otherBrace < builder.length && c != 0) {
                    otherBrace++
                    if (builder[otherBrace] == '(') {
                        c++
                    } else if (builder[otherBrace] == ')') {
                        c--
                    }
                }
                builder.replace(braceIndex, otherBrace + 1,
                        parse(builder.substring(braceIndex + 1, otherBrace), precedence).toString())
            } else {
                break
            }
        }

        val tokens = builder.toString().split(" ")

        return eval(shunt(tokens, precedence))
    }

    private fun shunt(tokens: List<String>, precedence: (String) -> Int) : List<String> {
        val output = mutableListOf<String>()
        val operatorStack = Stack<String>()

        tokens.forEach { token ->
            if(token.isInt()) {
                output += token
            } else if(token == "+" || token == "*") {
                while(!operatorStack.empty() && precedence(token) <= precedence(operatorStack.peek())) {
                    output += operatorStack.pop()
                }
                operatorStack += token
            }
        }
        while(!operatorStack.empty()) {
            output += operatorStack.pop()
        }

        return output
    }

    private fun eval(expr: List<String>) : Long {
        val stack = Stack<Long>()
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
