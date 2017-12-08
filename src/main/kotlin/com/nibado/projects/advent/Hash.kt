package com.nibado.projects.advent

import java.security.MessageDigest

object Hash {
    private val md5 = MessageDigest.getInstance("md5")!!

    fun md5(input: String) = md5.digest(input.toByteArray(Charsets.UTF_8)).toHex()
}