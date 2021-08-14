package com.fclass.memorygame

import android.animation.ArgbEvaluator
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fclass.memorygame.models.BoardSize
import com.fclass.memorygame.models.MemoryGame
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var clRoot: ConstraintLayout
    private lateinit var llGameInfo: LinearLayout
    private lateinit var memoryGame: MemoryGame
    private lateinit var rvBoard: RecyclerView
    private lateinit var tvNumMoves: TextView
    private lateinit var tvNumPairs: TextView
    private lateinit var adapter: MemoryBoardAdapter

    //creating an object of BoardSize class
    private var boardSize: BoardSize = BoardSize.MEDIUM




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
        llGameInfo = findViewById(R.id.llGameInfo)


        //setting up the game again on Refresh
        setupBoard()
    }






    //Inflating the Main Activity with the Menu Resource File that we created in menu_main.xml
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }





    //This is how we get notified of the user tapping on the Refresh button in the menu
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mi_refresh -> {
                //Setup the game again, Pressing of Refresh Button is detected
                setupBoard()

            }
        }
        return super.onOptionsItemSelected(item)
    }






    //all the logic to setup a new game ofter REFRESH
    private fun setupBoard() {
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

            val color = ArgbEvaluator().evaluate(
                memoryGame.numPairsFound.toFloat() / boardSize.getNumPairs(),
                ContextCompat.getColor(this, R.color.color_progress_none),
                ContextCompat.getColor(this, R.color.color_progress_full),

                ) as Int //casting the value of Argb Value as an Int because SetTextColor expects an Int
            tvNumPairs.setTextColor(color)
            tvNumPairs.setTypeface(null, Typeface.BOLD)
            tvNumPairs.text = "Pairs : ${memoryGame.numPairsFound} / ${boardSize.getNumPairs()}"

            if (memoryGame.haveWonGame()) {

                val snackbar =
                    Snackbar.make(clRoot, "🎉🎉🎉 LEVEL COMPLETE 🎉🎉🎉 ", Snackbar.LENGTH_LONG)
                snackbar.setAnchorView(llGameInfo) //so that snackbar appears above that llGame element
                snackbar.view.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.color_progress_full
                    )
                )
                snackbar.show()

            }
        }


        //update the number of moves the user has made
        tvNumMoves.text = "Moves :  ${memoryGame.getNumMoves()}"

        //once the card is flipped, we have to notify the adapter that it is changed
        adapter.notifyDataSetChanged()

    }
}



