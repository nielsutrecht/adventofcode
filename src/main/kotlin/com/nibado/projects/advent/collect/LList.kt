package com.nibado.projects.advent.collect

class LList<T>(private var next: LList<T>?, private var prev: LList<T>?, private var value: T) {
    fun first() : LList<T> = if(prev != null) prev!!.first() else this
    fun last() : LList<T> = if(next != null) next!!.first() else this

    fun addPrev(value: T) : LList<T> {
        if (prev == null) {
            prev = LList(this, null, value)
        }

        return prev!!
    }

    fun addNext(value: T) : LList<T> {
        if (next == null) {
            next = LList(null, this, value)
        }

        return next!!
    }

    fun hasNext() = next != null
    fun hasPrev() = prev != null

    fun next() = next!!
    fun prev() = prev!!

    fun values() : List<T> {
        val list = mutableListOf<T>()
        values(list)

        return list
    }

    fun value() = this.value
    fun value(value: T) { this.value = value }

    private fun values(list: MutableList<T>) {
        list += value
        if(next != null) {
            next!!.values(list)
        }
    }
}