package com.nibado.projects.advent.graphics

import com.nibado.projects.advent.Point
import com.nibado.projects.advent.collect.CharMap
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO


object Images {
    fun from(
            map: CharMap,
            colorMap: Map<Char, Color>,
            topLeft: Point = Point(0, 0),
            bottomRight: Point = Point(map.width - 1, map.height - 1)): BufferedImage {

        val dx = 0 - topLeft.x
        val dy = 0 - topLeft.y

        val img = BufferedImage(map.width + dx, map.height + dy, BufferedImage.TYPE_INT_RGB)

        for (y in topLeft.y..bottomRight.y) {
            for (x in topLeft.x..bottomRight.x) {
                val c = colorMap[map[x, y]]?.let { it.rgb } ?: 0
                img.setRGB(x + dx, y + dy, c)
            }
        }

        return img
    }

    fun write(img: BufferedImage, file: File) {
        ImageIO.write(img, file.extension, file)
    }
}