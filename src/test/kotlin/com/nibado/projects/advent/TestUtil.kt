package com.nibado.projects.advent

infix fun Int.point(y: Int) = Point(this, y)
infix fun Point.rect(right: Point) = Rectangle(this, right)
