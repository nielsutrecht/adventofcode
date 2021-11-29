package com.nibado.projects.advent.collect

class FreqMap : Map<Char, Int> {
    private val map = mutableMapOf<Char, Int>()

    override fun containsKey(key: Char) = map.containsKey(key)
    override fun containsValue(value: Int) = map.containsValue(value)
    override fun get(key: Char) = map[key]
    override fun isEmpty() = map.isEmpty()
    override val entries: MutableSet<MutableMap.MutableEntry<Char, Int>>
        get() = map.entries
    override val keys: MutableSet<Char>
        get() = map.keys
    override val values: MutableCollection<Int>
        get() = map.values
    override val size: Int
        get() = map.size

    fun inc(key: Char) {
        map[key] = map.computeIfAbsent(key, {0}) + 1
    }

    fun max() = entries.maxByOrNull { it.value }?.key
    fun min() = entries.minByOrNull { it.value }?.key

}