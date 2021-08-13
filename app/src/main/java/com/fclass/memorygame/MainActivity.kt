package com.fclass.memorygame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fclass.memorygame.models.BoardSize
import com.fclass.memorygame.models.MemoryCard
import com.fclass.memorygame.utils.DEFAULT_ICONS


class MainActivity : AppCompatActivity() {

    private lateinit var rvBoard:RecyclerView
    private lateinit var tvNumMoves:TextView
    private lateinit var tvNumPairs:TextView

    //creating an object of BoardSize class
    private var boardSize : BoardSize = BoardSize.HARD


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvBoard = findViewById(R.id.rvBoard)
        tvNumMoves = findViewById(R.id.tvNumMoves)
        tvNumPairs  = findViewById(R.id.tvNumPairs)


        //shuffled means random in kotlin and we take total number of pairs because we will need 12 icons for 24 grid
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        //doubling up the chosen images
        val randomizedImages = (chosenImages + chosenImages).shuffled()

        //Instead of passing just the Randomized Vector Icons we want to instead pass on
        //a MemoryCard object (of a data class) where it has properties like
        //1. Identifier which is the one element of the list of randomizedImages, 2. if the card is
        //faceup or facedown, 3. if the card is matched or not
        val memoryCards : List<MemoryCard> = randomizedImages.map{MemoryCard(identifier = it)}


        //second parameter in MemoryBoardAdapter is boardSize not boardSize.numCards because we made this
        //class and the data type for second parameter is not int but BoardSize
        rvBoard.adapter = MemoryBoardAdapter(this, boardSize, memoryCards)
        rvBoard.setHasFixedSize(true)
        rvBoard.layoutManager = GridLayoutManager(this, boardSize.getWidth())
    }
}