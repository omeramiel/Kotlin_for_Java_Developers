package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    var count = 0
    val list = permutation.toMutableList()
    list.run {
        while (this != sorted()) {
            forEachIndexed { index, i ->
                if (index + 1 < size && list[index + 1] < i) {
                    list[index] = list[index + 1]
                    list[index + 1] = i
                    count++
                }
            }
        }
    }
    return count % 2 == 0

}