package io.chessy.tool.view

import java.awt.Graphics
import java.awt.geom.Ellipse2D
import java.awt.image.BufferedImage
import java.net.URL
import javax.imageio.ImageIO

class AvatarView(
    override val x: Int = 0,
    override val y: Int = 0,
    private val url: URL?,
    private val size: Int
) : View {
    override val width: Int = size
    override val height: Int = size

    override fun draw(graphics: Graphics) {
        val avatar = ImageIO.read(url ?: pathToAvatar())
        val avatarWidth = avatar.width
        val circled = BufferedImage(avatarWidth, avatarWidth, BufferedImage.TYPE_INT_ARGB)
        val g = circled.createGraphics()
        g.clip = Ellipse2D.Float(0f, 0f, avatarWidth.toFloat(), avatarWidth.toFloat())
        g.drawImage(avatar, 0, 0, avatarWidth, avatarWidth, null)
        graphics.drawImage(circled, x, y, size, size, null)
    }

    override fun copy(x: Int, y: Int, width: Int, height: Int): View {
        return AvatarView(x, y, url, size)
    }

    private fun pathToAvatar(): URL {
        return PieceView::class.java.getResource("/user.png")!!
    }

}