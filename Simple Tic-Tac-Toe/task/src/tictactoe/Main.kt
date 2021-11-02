package tictactoe

class GameTicTacToe(val board: MutableList<MutableList<Char>> = MutableList(3){MutableList(3){'_'}}) {
    var dot = 'X'
    init {
        printBoard()
        mainLoop()
    }

    private fun printBoard() {
        println("---------")
        for (index in 0..2) {
            println("| ${board[index].joinToString(" ")} |")
        }
        println("---------")
    }

    private fun mainLoop() {
        do {
            do {
                println("Enter the coordinates:")
                val input: MutableList<String> = readLine()!!.split(" ").toMutableList()
                input.removeAll { it == "" }
            } while (!checkInputAndCoordinatesAndPlaceDot(input))
        } while (!checkWinAndDrawChangeDotIfFalse())
    }

    private fun checkInputAndCoordinatesAndPlaceDot(input: List<String>): Boolean {
        fun checkCoordForEmpty(): Boolean {
            return board[input[0].toInt() - 1][input[1].toInt() - 1] == '_'
        }
        var bool = false
        if (input.size != 2) {
            println("Coordinates must consist of two values divided by space, like this: (1 1)")
            return bool
        }
        for (i in input) {
            if (!i.matches(Regex("\\d+")) || i.isEmpty()) {
                println("You should enter numbers!")
                return bool
            } else if (!i.matches(Regex("[1-3]"))) {
                println("Coordinates should be from 1 to 3!")
                return bool
            }
        }
        if (!checkCoordForEmpty()) {
            println("This cell is occupied! Choose another one!")
        } else {
            this.board[input[0].toInt() - 1][input[1].toInt() - 1] = dot
            printBoard()
            bool = true
        }
        return bool
    }

    private fun checkWinAndDrawChangeDotIfFalse(): Boolean {
        return if (!checkWin() && !checkDraw()) {
            dot = if (dot == 'X') 'O' else 'X'
            false
        } else true
    }

    private fun checkWin(): Boolean {
        return if (checkHorizontal() || checkVertical() || checkDiagonals()) {
            println("$dot wins")
            true
        } else false
    }

    private fun checkHorizontal(): Boolean {
        for (list in board) {
            if (list.count{ it == dot} == 3) {
                return true
            }
        }
        return false
    }

    private fun checkVertical(): Boolean {
        for (lastIndex in 0..2) {
            val list = mutableListOf<Char>()
            for (firstIndex in 0..2) {
                list.add(board[firstIndex][lastIndex])
            }
            if (list.count { it == dot } == 3) {
                return true
            }
        }
        return false
    }

    private fun checkDiagonals(): Boolean {
        val list1 = mutableListOf(board[0][0], board[1][1], board[2][2])
        val list2 = mutableListOf(board[2][0], board[1][1], board[0][2])
        return list1.count{ it == dot} == 3 || list2.count { it == dot } == 3
    }

    private fun checkDraw() : Boolean {
        var countSpace = 0
        for (list in board) {
            countSpace += list.count { it == '_' }
        }
        return if (countSpace == 0 && !checkWin()) {
            println("Draw")
            true
        } else false
    }
}

fun main() {
    val game = GameTicTacToe()
}