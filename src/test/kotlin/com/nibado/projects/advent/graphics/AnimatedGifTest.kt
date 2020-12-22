package com.nibado.projects.advent.graphics

import org.junit.jupiter.api.Test
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File

internal class AnimatedGifTest {
    @Test
    fun test() {
        val anim = AnimatedGif()
        anim += {createImage(Color.RED) }
        anim += {createImage(Color.GREEN) }
        anim += {createImage(Color.BLUE) }

        anim.encodeTo(File("anim.gif"))
    }

    private fun createImage(color: Color) : BufferedImage {
        val image = BufferedImage(240, 240, BufferedImage.TYPE_INT_RGB)

        with(image.createGraphics()) {
            setColor(color)
            fillRect(0,0,240,240)
        }

        return image
    }
}