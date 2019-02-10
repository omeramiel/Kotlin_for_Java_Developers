package board

open class SquareBoardImpl(override val width: Int, internal val cells: Array<Array<Cell>> = Array(width) { i ->
    Array(width) { j ->
        Cell(i + 1, j + 1)
    }
}) : SquareBoard {

    override fun getAllCells(): Collection<Cell> {
        return cells.flatMap { it.asIterable() }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        return try {
            cells[i - 1][j - 1]
        } catch (e: ArrayIndexOutOfBoundsException) {
            null
        }
    }

    override fun getCell(i: Int, j: Int): Cell {
        return cells[i - 1][j - 1]
    }

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> {
        val row = mutableListOf<Cell>()
        jRange.forEach { j ->
            if (j - 1 < width) {
                row.add(cells[i - 1][j - 1])
            }
        }
        return row
    }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> {
        val column = mutableListOf<Cell>()
        iRange.forEach { i ->
            if (j - 1 < width) {
                column.add(cells[i - 1][j - 1])
            }
        }
        return column
    }

    override fun Cell.getNeighbour(direction: Direction): Cell? {
        return when (direction) {
            Direction.UP -> getCellOrNull(i - 1, j)
            Direction.DOWN -> getCellOrNull(i + 1, j)
            Direction.LEFT -> getCellOrNull(i, j - 1)
            Direction.RIGHT -> getCellOrNull(i, j + 1)
        }
    }
}

class GameBoardImpl<T>(override val width: Int) : SquareBoardImpl(width), GameBoard<T> {

    private val map = HashMap<Cell, T?>(width * width)

    init {
        cells.flatten().associateTo(map) { it to null }
    }

    override fun get(cell: Cell): T? {
        return map[cell]
    }

    override fun set(cell: Cell, value: T?) {
        map[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> {
        return map.filterValues(predicate).keys
    }

    override fun find(predicate: (T?) -> Boolean): Cell? {
        return filter(predicate).single()
    }

    override fun any(predicate: (T?) -> Boolean): Boolean {
        return map.filterValues(predicate).any()
    }

    override fun all(predicate: (T?) -> Boolean): Boolean {
        return map.values.all(predicate)
    }

}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl(width)
