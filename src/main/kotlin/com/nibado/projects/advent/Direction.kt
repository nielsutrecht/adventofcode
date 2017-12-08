package com.nibado.projects.advent

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
}