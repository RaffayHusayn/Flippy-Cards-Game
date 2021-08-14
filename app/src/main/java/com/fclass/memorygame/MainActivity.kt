package com.fclass.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fclass.memorygame.models.BoardSize
import com.fclass.memorygame.models.MemoryGame


class MainActivity : AppCompatActivity() {

    private lateinit var memoryGame: MemoryGame
    private lateinit var rvBoard:RecyclerView
    private lateinit var tvNumMoves:TextView
    private lateinit var tvNumPairs:TextView
    private lateinit var adapter: MemoryBoardAdapter

    //creating an object of BoardSize class
    private var boardSize : BoardSize = BoardSize.HARD



    companion object{
        private const val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard = findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs  = findViewById(R.id.tvNumPairs)


        memoryGame = MemoryGame(boardSize)
        //second parameter in MemoryBoardAdapter is boardSize not boardSize.numCards because we made this
        //class and the data type for second parameter is not int but BoardSize
        adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object: MemoryBoardAdapter.CardClickListener{
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
        if (memoryGame.haveWonGame()){

            //notify user
            return
        }

        //2. Card already flipped
        if(memoryGame.isCardFaceIp(position)){

            //don't let user do anything to that card
            return
        }



        memoryGame.flipCard(position)
        //once the card is flipped, we have to notify the adapter that it is changed
        adapter.notifyDataSetChanged()

    }
}