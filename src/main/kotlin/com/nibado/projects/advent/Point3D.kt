package com.nibado.projects.advent

import java.lang.Integer.max
import java.lang.Integer.min

data class Point3D(val x: Int, val y: Int, val z: Int) {

    constructor() : this(0, 0, 0)

    fun neighbors() = neighbors(this)

    operator fun plus(other: Point3D) = Point3D(x + other.x, y + other.y, z + other.z)
    operator fun minus(other: Point3D) = Point3D(x - other.x, y - other.y, z - other.z)

    companion object {
        private val MAX = Point3D(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
        private val MIN = Point3D(Int.MIN_VALUE, Int.MIN_VALUE, Int.MIN_VALUE)
        private val ORIGIN = Point3D()

        fun neighbors(p: Point3D): List<Point3D> =
                (-1..1).flatMap { x ->
                    (-1..1).flatMap { y ->
                        (-1..1)
                                .map { z -> Point3D(x + p.x, y + p.y, z + p.z) }
                    }
                }
                        .filterNot { (x, y, z) -> x == p.x && y == p.y && z == p.z }

        fun bounds(points: Collection<Point3D>) = points
                .fold(MAX to MIN) { (min, max), p ->
                    Point3D(min(min.x, p.x), min(min.y, p.y), min(min.z, p.z)) to
                            Point3D(max(max.x, p.x), max(min.y, p.y), max(max.z, p.z))
                }

        fun points(min: Point3D, max: Point3D): List<Point3D> = (min.x..max.x).flatMap { x ->
            (min.y..max.y).flatMap { y -> (min.z..max.z).map { z -> Point3D(x, y, z) } }
        }

    }
}

fun Pair<Point3D, Point3D>.points() = let { (min, max) -> Point3D.points(min, max) }
fun Collection<Point3D>.bounds() = Point3D.bounds(this)
