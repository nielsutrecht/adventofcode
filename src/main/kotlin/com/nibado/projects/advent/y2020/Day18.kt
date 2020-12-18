package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.Stack

object Day18 : Day {
    private val values = resourceLines(2020, 18).map {
        it.replace(" ", "").split("").filterNot(String::isBlank)
    }

    override fun part1() = values.map { eval(shunt(it) { 1 } )}.sum()
    override fun part2() = values.map { eval(shunt(it) { t -> if (t == "+") 2 else 1 } )}.sum()

    private fun shunt(tokens: List<String>, precedence: (String) -> Int) : List<String> {
        val output = mutableListOf<String>()
        val operatorStack = Stack<String>()

        tokens.forEach { token ->
            if(token.isInt()) {
                output += token
            } else if(token == "+" || token == "*") {
                while(operatorStack.peekOrNull() in setOf("*", "+")
                        && precedence(token) <= precedence(operatorStack.peek())
                        && operatorStack.peek() != "(") {
                    output += operatorStack.pop()
                }
                operatorStack += token
            } else if(token == "(") {
                operatorStack += token
            } else if(token == ")") {
                while(operatorStack.peekOrNull() != "(") {
                    output += operatorStack.pop()
                }
                if(operatorStack.peek() == "(") {
                    operatorStack.pop()
                }
            }
        }

        return output + operatorStack
    }

    private fun eval(expr: List<String>) : Long {
        val stack = Stack<Long>()
        expr.forEach {
            stack += if(it.isInt()) {
                it.toLong()
            } else {
                val a = stack.pop()
                val b = stack.pop()
                when(it) {
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
