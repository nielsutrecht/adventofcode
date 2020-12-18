package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import com.nibado.projects.advent.collect.Stack

object Day18 : Day {
    private val values = resourceLines(2020, 18).map {
        it.replace(" ", "").split("").filterNot(String::isBlank)
    }

    override fun part1() = values.map { eval(shunt(it) { 1 } )}.sum()
    override fun part2() = values.map { eval(shunt(it) { t -> if (t == "+") 2 else 1 } )}.sum()

    private fun shunt(tokens: List<String>, precedence: (String) -> Int) : List<String> =
        tokens.fold(mutableListOf<String>() to Stack<String>()) { (output, operators), token ->
            if(token.isInt()) {
                output += token
            } else if(token == "+" || token == "*") {
                while(operators.peekOrNull() in setOf("*", "+")
                        && precedence(token) <= precedence(operators.peek())
                        && operators.peek() != "(") {
                    output += operators.pop()
                }
                operators += token
            } else if(token == "(") {
                operators += token
            } else if(token == ")") {
                while(operators.peekOrNull() != "(") {
                    output += operators.pop()
                }
                if(operators.peek() == "(") {
                    operators.pop()
                }
            }

            output to operators
        }.let { (output,operators) -> output + operators }

    private fun eval(expr: List<String>) : Long = expr.fold(Stack<Long>()) { s, e ->
                s += when(e) {
                    "+" -> s.pop() + s.pop()
                    "*" -> s.pop() * s.pop()
                    else -> e.toLong()
                }
            s
        }.pop()
}
