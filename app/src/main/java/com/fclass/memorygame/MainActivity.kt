package com.fclass.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fclass.memorygame.models.BoardSize
import com.fclass.memorygame.models.MemoryGame
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var clRoot: ConstraintLayout
    private lateinit var memoryGame: MemoryGame
    private lateinit var rvBoard: RecyclerView
    private lateinit var tvNumMoves: TextView
    private lateinit var tvNumPairs: TextView
    private lateinit var adapter: MemoryBoardAdapter

    //creating an object of BoardSize class
    private var boardSize: BoardSize = BoardSize.EASY


    companion object {
        private const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        clRoot = findViewById(R.id.clRoot)
        rvBoard = findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs = findViewById(R.id.tvNumPairs)


        memoryGame = MemoryGame(boardSize)
        //second parameter in MemoryBoardAdapter is boardSize not boardSize.numCards because we made this
        //class and the data type for second parameter is not int but BoardSize
        adapter = MemoryBoardAdapter(
            this,
            boardSize,
            memoryGame.cards,
            object : MemoryBoardAdapter.CardClickListener {
                override fun onCardClicked(position: Int) {
                    updateGameWithFlip(position)
                }

            })
        rvBoard.adapter = adapter
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }


    private fun updateGameWithFlip(position: Int) {
        //Error Handling
        //1. Already won the game
        if (memoryGame.haveWonGame()) {

            //notify user that he already won
            Snackbar.make(clRoot, "Idiot, you already won!!", Snackbar.LENGTH_LONG).show()
            return
        }

        //2. Card already flipped
        if (memoryGame.isCardFaceIp(position)) {

            //don't let user do anything to that card
            return
        }


        //Actually flipping the card, FLIPCARD returns true when a match is found
        if (memoryGame.flipCard(position)) {
            Log.i(TAG, "Found a match, no of pairs found :${memoryGame.numPairsFound}")
            tvNumPairs.text = "Pairs : ${memoryGame.numPairsFound} / ${boardSize.getNumPairs()}"

            if(memoryGame.haveWonGame()){
                Snackbar.make(clRoot, "Congrats, you won", Snackbar.LENGTH_SHORT).show()
            }
        }
        //once the card is flipped, we have to notify the adapter that it is changed
        adapter.notifyDataSetChanged()

    }
}