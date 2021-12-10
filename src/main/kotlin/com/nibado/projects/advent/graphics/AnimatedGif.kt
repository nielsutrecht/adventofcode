package com.nibado.projects.advent.graphics

import com.madgag.gif.fmsware.AnimatedGifEncoder
import java.awt.image.BufferedImage
import java.io.*

class AnimatedGif(private val repeat:Int = 0, private val delay: Int = 1000) {
    private val imageStates = mutableListOf<() -> BufferedImage>()
    private var frameRate: Float? = null

    operator fun plusAssign(image: () -> BufferedImage) {
        imageStates += image
    }

    operator fun plusAssign(image: BufferedImage) {
        imageStates += { image }
    }

    fun frameRate(frameRate: Float) = also { this.frameRate = frameRate }
    fun frameRate(frameRate: Int) = frameRate(frameRate.toFloat())

    fun encodeTo(file: File) {
        encodeTo(file.outputStream())
    }

    fun encodeTo(out: OutputStream) {
        val encoder = AnimatedGifEncoder()
        encoder.setRepeat(repeat)
        encoder.setDelay(delay)
        frameRate?.let { encoder.setFrameRate(frameRate!!) }

        out.use {
            encoder.start(it)

            imageStates.forEach {
                encoder.addFrame(it())

            }

            encoder.finish()
        }
    }
}