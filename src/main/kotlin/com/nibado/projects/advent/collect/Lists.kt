package com.nibado.projects.advent.collect

fun <T> List<T>.reverse(index: Int, length: Int) : List<T> {
    val copy = toMutableList()

    reverseMutable(copy, index, length);

    return copy
}

fun <T> List<T>.swap(a: Int, b: Int) : List<T> {
    val copy = toMutableList()

    swap(copy, a, b)

    return copy
}

fun <T> List<T>.swapByValue(a: T, b: T) = swap(indexOf(a), indexOf(b))

fun <T> List<T>.swap(pair: Pair<Int, Int>) = this.swap(pair.first, pair.second)

fun <T> swap(list: MutableList<T>, a: Int, b: Int) {
    val temp = list[a]
    list[a] = list[b]
    list[b] = temp
}

fun <T> swap(list: MutableList<T>, pair: Pair<Int, Int>) = swap(list, pair.first, pair.second)

fun <T> List<T>.move(from: Int, to: Int) : List<T> {
    val copy = toMutableList()

    move(copy, from, to)

    return copy
}

fun <T> move(list: MutableList<T>, from: Int, to: Int) {
    val temp = list.removeAt(from)
    list.add(to, temp)
}

fun <T> List<T>.rotateLeft(amount: Int): List<T> {
    val amt = amount % size

    return subList(amt, size) + subList(0, amt)
}

fun <T> List<T>.rotateRight(amount: Int): List<T> {
    val amt = amount % size

    return subList(size - amt, size) + subList(0, size - amt)
}

fun <T> reverseMutable(list: MutableList<T>, index: Int, length: Int) {
    val indices = (index until index + length).map { it % list.size }
    val subList = list.slice(indices).reversed()
    indices.forEachIndexed { i, v -> list[v] = subList[i] }
}

fun <T> List<T>.permutations(): List<List<T>> {
    val permutations: MutableList<List<T>> = mutableListOf()

    permutate(this, listOf(), permutations)

    return permutations
}

private fun <T> permutate(head: List<T>, tail: List<T>, permutations: MutableList<List<T>>) {
    if (head.isEmpty()) {
        permutations += tail
        return
    }

    for (i in 0 until head.size) {
        val newHead = head.filterIndexed({ index, _ -> index != i }).toList()
        val newTail = tail + head[i]

        permutate(newHead, newTail, permutations)
    }
}