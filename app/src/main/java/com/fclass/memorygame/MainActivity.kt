package com.fclass.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fclass.memorygame.models.BoardSize
import com.fclass.memorygame.models.MemoryCard
import com.fclass.memorygame.models.MemoryGame
import com.fclass.memorygame.utils.DEFAULT_ICONS


class MainActivity : AppCompatActivity() {

    private lateinit var rvBoard:RecyclerView
    private lateinit var tvNumMoves:TextView
    private lateinit var tvNumPairs:TextView

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


        val memoryGame = MemoryGame(boardSize)
        //second parameter in MemoryBoardAdapter is boardSize not boardSize.numCards because we made this
        //class and the data type for second parameter is not int but BoardSize
        rvBoard.adapter = MemoryBoardAdapter(this, boardSize, memoryGame.cards, object: MemoryBoardAdapter.CardClickListener{
            override fun onCardClicked(position: Int) {
                Log.i(TAG, "card is clicked at $position")
            }

        })
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }
}