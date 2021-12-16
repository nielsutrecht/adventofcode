package com.nibado.projects.advent.collect

class StringTokenizer(val value: String) {
    private var index = 0
    val read: Int
        get() = index

    constructor(seq: CharSequence) : this(seq.toString())

    fun take(amount: Int) : String {
        val s = value.substring(index, index + amount)
        index += amount

        return s
    }

    override fun toString(): String = value.substring(index)
}