package com.example.tictactoe

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private var currentPlayer = "X"
    private val board = Array(3) { arrayOfNulls<String>(3) }
    private var movesCount = 0
    private lateinit var currentPlayerText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        currentPlayerText = findViewById(R.id.currentPlayerText)

        val buttons = arrayOf(
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7),
            findViewById<Button>(R.id.button8),
            findViewById<Button>(R.id.button9)
        )

        buttons.forEachIndexed { index, button ->
            val row = index / 3
            val col = index % 3
            button.setOnClickListener { onButtonClick(button, row, col) }
        }

        findViewById<Button>(R.id.resetButton).setOnClickListener {
            resetGame(buttons)
        }

        updateCurrentPlayerText()
    }

    private fun onButtonClick(button: Button, row: Int, col: Int) {
        if (button.text.isNotEmpty()) {
            Toast.makeText(this, "Cell already taken!", Toast.LENGTH_SHORT).show()
            return
        }

        button.text = currentPlayer
        board[row][col] = currentPlayer
        movesCount++

        if (checkWinner(row, col)) {
            Toast.makeText(this, "$currentPlayer wins!", Toast.LENGTH_LONG).show()
            disableBoard()
        } else if (movesCount == 9) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_LONG).show()
        } else {
            currentPlayer = if (currentPlayer == "X") "O" else "X"
            updateCurrentPlayerText()
        }
    }

    private fun updateCurrentPlayerText() {
        currentPlayerText.text = "Player $currentPlayer's turn"
    }

    private fun checkWinner(row: Int, col: Int): Boolean {
        if (board[row].all { it == currentPlayer }) return true

        if (board.all { it[col] == currentPlayer }) return true

        if (row == col && board.indices.all { board[it][it] == currentPlayer }) return true
        if (row + col == 2 && board.indices.all { board[it][2 - it] == currentPlayer }) return true

        return false
    }

    private fun disableBoard() {
        val buttons = listOf(
            findViewById<Button>(R.id.button1),
            findViewById<Button>(R.id.button2),
            findViewById<Button>(R.id.button3),
            findViewById<Button>(R.id.button4),
            findViewById<Button>(R.id.button5),
            findViewById<Button>(R.id.button6),
            findViewById<Button>(R.id.button7),
            findViewById<Button>(R.id.button8),
            findViewById<Button>(R.id.button9)
        )
        buttons.forEach { it.isEnabled = false }
    }

    private fun resetGame(buttons: Array<Button>) {
        buttons.forEach {
            it.text = ""
            it.isEnabled = true
        }
        for (row in board) {
            row.fill(null)
        }
        currentPlayer = "X"
        movesCount = 0
        updateCurrentPlayerText()
    }
}

