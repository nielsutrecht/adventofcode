package com.nibado.projects.advent

import java.lang.IllegalArgumentException

enum class Direction {
    NORTH,
    EAST,
    SOUTH,
    WEST;

    fun cw() = when(this) {
        NORTH -> EAST
        EAST -> SOUTH
        SOUTH -> WEST
        WEST -> NORTH
    }

    fun ccw() = when(this) {
        NORTH -> WEST
        WEST -> SOUTH
        SOUTH -> EAST
        EAST -> NORTH
    }

    companion object {
        fun from(s: String) : Direction = from(s.first())
        fun from(char: Char) : Direction = when(char.toUpperCase()) {
            'N' -> NORTH
            'S' -> SOUTH
            'E' -> EAST
            'W' -> WEST

            'R' -> EAST
            'L' -> WEST
            'U' -> NORTH
            'D' -> SOUTH
            else -> throw IllegalArgumentException("Can't map $char to Direction")
        }
    }
}