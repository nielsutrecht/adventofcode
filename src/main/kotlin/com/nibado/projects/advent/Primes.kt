package com.nibado.projects.advent

import java.util.*

object Primes {
    const val MAX = 1000_000
    val bitmap: BitSet by lazy { Primes.sieve(MAX) }
    val primes: List<Int> by lazy { (1 .. MAX).filter { isPrime(it) }.toList() }

    fun sieve(max: Int) : BitSet {
        val primeMap = BitSet(max)
        primeMap.set(0, primeMap.size() - 1)
        primeMap.set(0, false)

        for(i in 2 .. primeMap.size()) {
            if(primeMap[i - 1]) {
                for(j in i + i .. primeMap.size() step i) {
                    primeMap.set(j - 1, false)
                }
            }
        }

        return primeMap
    }

    fun isPrime(num: Int) = bitmap[num - 1]
    fun nthPrime(n: Int) = primes[n - 1]
}