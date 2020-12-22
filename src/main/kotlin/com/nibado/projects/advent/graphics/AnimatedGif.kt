package com.nibado.projects.advent.graphics

import com.madgag.gif.fmsware.AnimatedGifEncoder
import java.awt.image.BufferedImage
import java.io.File
import java.io.OutputStream

class AnimatedGif(private val repeat:Int = 0, private val delay: Int = 1000) {
    private val imageStates = mutableListOf<() -> BufferedImage>()

    operator fun plusAssign(image: () -> BufferedImage) {
        imageStates += image
    }

    operator fun plusAssign(image: BufferedImage) {
        imageStates += { image }
    }

    fun encodeTo(file: File) {
        encodeTo(file.outputStream())
    }

    fun encodeTo(out: OutputStream) {
        val encoder = AnimatedGifEncoder()
        encoder.setRepeat(repeat)
        encoder.setDelay(delay)

        out.use {
            encoder.start(it)

            imageStates.forEach {
                encoder.addFrame(it())

            }

            encoder.finish()
        }
    }
}