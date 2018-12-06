package com.nibado.projects.advent

data class Rectangle(val left: Point, val right: Point) {

    fun points() : Sequence<Point> = (left.x .. right.x).flatMap { x -> (left.y .. right.y).map { y -> Point(x, y) } }.asSequence()

    fun area() : Int {
        return Math.abs(right.x - left.x) * Math.abs(right.y - left.y)
    }

    fun onEdge(p: Point) = p.x == left.x || p.y == left.y || p.x == right.x || p.y == right.y

    fun intersect(other: Rectangle) : Rectangle? {
        val xL = Math.max(left.x, other.left.x)
        val xR = Math.min(right.x, other.right.x)
        val yT = Math.max(left.y, other.left.y)
        val yB = Math.min(right.y, other.right.y)

        if (xR <= xL || yB <= yT)
            return null

        return Rectangle(Point(xL, yT), Point(xR, yB))
    }

    companion object {
        fun of(point: Point, width: Int, height: Int) =
                Rectangle(point, point.add(width - 1, height - 1))

        fun of(left: Point, right: Point) =
                Rectangle(left, right)
    }
}