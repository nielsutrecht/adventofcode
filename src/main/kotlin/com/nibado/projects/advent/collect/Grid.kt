package com.nibado.projects.advent.collect

import com.nibado.projects.advent.*

interface Grid<T> : Iterable<Pair<Point, T>>{
    val width: Int
    val height: Int
    val elements: List<T>

    val size: Int
        get() = width * height

    val bounds: Rectangle
        get() = Rectangle(Point.ORIGIN, Point(width - 1, height - 1))

    val indices: Sequence<Pair<Int, Int>>
        get() = (0 until height).asSequence().flatMap { y -> (0 until width).asSequence().map { x -> x to y } }

    val points: Sequence<Point>
        get() = indices.map { (x, y) -> Point(x, y) }

    fun toIndex(x: Int, y: Int) : Int {
        val index = width * y + x
        if(index < 0 || index >= elements.size) {
            throw IndexOutOfBoundsException("Index $x, $y out of bounds 0..${width - 1}, 0..${height - 1}")
        }
        return index
    }
    fun toPoint(index: Int) = Point(index % width, index / width)

    fun inBound(p: Point): Boolean = p.x >= 0 && p.y >= 0 && p.x < width && p.y < height

    fun apply(map: (Point, T) -> T)
    fun apply(map: (Int, Int, T) -> T)

    fun row(r: Int) = (0 until width).map { this[it, r] }
    fun column(c: Int) = (0 until height).map { this[c, it] }

    operator fun get(p: Point) : T = elements[toIndex(p.x, p.y)]
    operator fun get(x: Int, y: Int) : T = elements[toIndex(x, y)]
    operator fun set(p: Point, value: T)
    operator fun set(x: Int, y: Int, value: T)

    override fun iterator(): Iterator<Pair<Point, T>> {
        return object : Iterator<Pair<Point, T>> {
            private var index = 0
            override fun hasNext(): Boolean = index < width * height

            override fun next(): Pair<Point, T> {
                return toPoint(index) to elements[index++]
            }
        }
    }
}