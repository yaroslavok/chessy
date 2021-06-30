package io.chessy.tool.view

import io.chessy.tool.chess.Board
import java.awt.Color
import java.awt.Graphics

class RootView(
    override val x: Int,
    override val y: Int,
    override val width: Int,
    override val height: Int,
    private val graphicsContext: Graphics,
    private val board: Board,
) : ViewGroup<GameView.GameViewAction>() {

    private val gameView: ViewGroup<GameView.GameViewAction>

    init {
        val playerDetailView2 = PlayerDetailsView(
            width = width,
            playerName = "Магнус Карлсен",
            playerRating = 2910,
            playerIconName = "magnus.jpg",
            graphicsContext = graphicsContext
        ).moveWithSize { _, viewHeight -> 0 to height - viewHeight }
        gameView = BorderedGameView(
            x,
            playerDetailView2.y - width,
            width,
            graphicsContext, board
        )
        val playerDetailView = PlayerDetailsView(
            x = 0,
            y = gameView.y - playerDetailView2.height,
            width = width,
            playerName =  "Ян Непомнящий",
            playerRating = 2891,
            playerIconName = "yan.jpg",
            graphicsContext = graphicsContext
        )
        addChild(OnceDrawView(playerDetailView2))
        addChild(OnceDrawView(playerDetailView))
        addChild(gameView)
    }

    override fun obtainAction(action: GameView.GameViewAction) {
        gameView.produceAction(action)
    }

    override fun copy(x: Int, y: Int, width: Int, height: Int): View {
        return RootView(x, y, width, height, graphicsContext, board)
    }

    override fun draw(graphics: Graphics) {
        graphics.color = backgroundColor
        graphics.fillRect(x, y, width, height)
        super.draw(graphics)
    }

    companion object {
        private val backgroundColor = Color.decode("#212121")
    }
}