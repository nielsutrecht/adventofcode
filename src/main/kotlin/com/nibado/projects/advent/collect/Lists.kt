package com.nibado.projects.advent.collect



fun reverse(list: List<Any>, index: Int, length: Int) : List<Any> {
    val copy = list.toMutableList()

    reverseMutable(copy, index, length);

    return copy
}

fun swap(list: List<Any>, a: Int, b: Int) : List<Any> {
    val copy = list.toMutableList()

    swapMutable(copy, a, b)

    return copy
}

fun swap(list: List<Any>, pair: Pair<Int, Int>) = swap(list, pair.first, pair.second)

fun rotateLeft(list: List<Any>, amount: Int): List<Any> {
    val amt = amount % list.size

    return list.subList(amt, list.size) + list.subList(0, amt)
}

fun rotateRight(list: List<Any>, amount: Int): List<Any> {
    val amt = amount % list.size

    return list.subList(list.size - amt, list.size) + list.subList(0, list.size - amt)
}

fun reverseMutable(list: MutableList<Any>, index: Int, length: Int) {
    val indices = (index until index + length).map { it % list.size }
    val subList = list.slice(indices).reversed()
    indices.forEachIndexed { i, v -> list[v] = subList[i] }
}

fun swapMutable(list: MutableList<Any>, a: Int, b: Int) {
    val temp = list[a]
    list[a] = list[b]
    list[b] = temp
}

fun swapMutable(list: MutableList<Any>, pair: Pair<Int, Int>) = swapMutable(list, pair.first, pair.second)
