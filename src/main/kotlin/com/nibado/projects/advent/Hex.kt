package com.nibado.projects.advent

object Hex {
    val neighbors = Direction.values().map { it.point }

    fun neighbors(point: Point3D) = neighbors.map { it + point }

    enum class Direction(val point: Point3D) {
        E(Point3D(1, -1, 0)),
        W(Point3D(-1, 1, 0)),
        SE(Point3D(0, -1, +1)),
        SW(Point3D(-1, 0, 1)),
        NE(Point3D(1, 0, -1)),
        NW(Point3D(0, 1, -1));
    }
}