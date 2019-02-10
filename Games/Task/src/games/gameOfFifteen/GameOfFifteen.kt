package games.gameOfFifteen

import board.Direction
import board.Cell
import board.Direction.*
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {

    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        board.addValues(initializer.initialPermutation)
    }

    override fun canMove(): Boolean {
        return true
    }

    override fun hasWon(): Boolean {
        board.getAllCells()
                .mapNotNull { board[it] }
                .run {
                    return this == sortedBy { it }
                }
    }

    override fun processMove(direction: Direction) {
        board.moveValues(direction)
    }

    override fun get(i: Int, j: Int): Int? {
        return board[Cell(i, j)]
    }

}

private fun GameBoard<Int?>.moveValues(direction: Direction) {
    val newDirection = when (direction) {
        UP -> DOWN
        DOWN -> UP
        RIGHT -> LEFT
        LEFT -> RIGHT
    }
    find { it == null }?.run {
        getNeighbour(newDirection)?.let {
            val value = this@moveValues[it]
            this@moveValues[this] = value
            this@moveValues[it] = null
        }
    }

}

private fun GameBoard<Int?>.addValues(values: List<Int>) {
    val range = 1..4
    for (i in range) {
        for (j in range) {
            val position = ((i - 1) * 4) + (j - 1)
            if (position < values.size) {
                this[Cell(i, j)] = values[position]
            }
        }
    }
}

