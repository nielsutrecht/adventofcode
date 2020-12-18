
package com.nibado.projects.advent.y2020

import com.nibado.projects.advent.*
import java.lang.StringBuilder
import java.util.*


object Day18 : Day {

    private val values = resourceLines(2020, 18)
    private val operators = setOf("+", "-", "*", "/")

    private fun precedence(token: String, other: String) : Boolean = true

    override fun part1() = values.map(::parse).sum() //6923486965641
    override fun part2() = TODO()

    fun parse(input: String) : Long {
        //println(input)
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
                builder.replace(braceIndex, otherBrace + 1, parse(builder.substring(braceIndex + 1, otherBrace)).toString())
            } else {
                break
            }
        }

        val tokens = builder.toString().split(" ")

        var i = 0
        var acc : Long? = null
        while(i < tokens.size) {
            if(tokens[i].isInt()) {
                acc = tokens[i].toLong()
                i++
            } else {
                val op = tokens[i]
                val next = tokens[i + 1].toLong()
                i += 2
                when(op) {
                    "*" -> acc = acc!! * next
                    "/" -> acc = acc!! / next
                    "+" -> acc = acc!! + next
                    "-" -> acc = acc!! - next
                }
            }
        }

        return acc!!
    }



}

fun main() {
    println(Day18.part1())

}
