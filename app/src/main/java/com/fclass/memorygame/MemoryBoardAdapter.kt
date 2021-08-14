package com.fclass.memorygame

import  android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.fclass.memorygame.models.BoardSize
import com.fclass.memorygame.models.MemoryCard
import com.fclass.memorygame.models.MemoryGame
import kotlin.math.min


class MemoryBoardAdapter(
    private val context: Context,
    private val boardSize: BoardSize,
    private val cards: List<MemoryCard>,
    private val cardClickListener: CardClickListener
) :
    RecyclerView.Adapter<MemoryBoardAdapter.ViewHolder>() {

    //companion object is similar to static variable in java that we don't need an instance of an object
    //to call it, it can be evoked directly from the class without an object first being created
    companion object {
        private const val MARGIN_SIZE = 10 //Margin around the CardViews

        //this TAG will appear in the Info Logs because Log.i(TAG, "$postion") so we can easily go
        //in Logcat filter info Log because of Log.i and search for MemoryBoardAdapter to find the log
        //in this case it is linked to a ClickListener on cardView
        private const val TAG = "MemoryBoardAdapter"
    }

    //creating an interface to notify the game if a card is clicked
    interface CardClickListener {
        fun onCardClicked(position: Int)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val cardWidth =
            parent.width / boardSize.getWidth() - (2 * MARGIN_SIZE)//hardcoding for 2 columns, parent is the RecyclerView
        val cardHeight =
            parent.height / boardSize.getHeight() - (2 * MARGIN_SIZE)//hardcoding for 4 rows, parent is the RecyclerView
        //to make the card square, just take the smaller measurement
        val cardSideLength = min(cardWidth, cardHeight)

        val view = LayoutInflater.from(context).inflate(R.layout.memory_card, parent, false)


        //grabbing out the cardView from the view that we have inflated in LayoutInflater
        val layoutParams =
            view.findViewById<CardView>(R.id.cardView).layoutParams as ViewGroup.MarginLayoutParams
        //dynamically setting the dimension of the card, both same bc we want it to be a square
        layoutParams.height = cardSideLength
        layoutParams.width = cardSideLength
        //setting viewGroup.MarginLayoutParam allows us to set the margin
        layoutParams.setMargins(MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE, MARGIN_SIZE)


        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = boardSize.numCards


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
        fun bind(position: Int) {


            val memoryCard = cards[position]
            //binding Vector Images to the cards
            imageButton.setImageResource(if (memoryCard.isFaceUp) memoryCard.identifier else R.drawable.ic_launcher_background)

            //setting the transparency of found pairs
            imageButton.alpha = if (memoryCard.isMatched) .4f else 1.0f
            val colorStateList = if (memoryCard.isMatched) ContextCompat.getColorStateList(
                context,
                R.color.color_green
            ) else null
            ViewCompat.setBackgroundTintList(imageButton, colorStateList)


            imageButton.setOnClickListener {

                Log.i(TAG, "clicked on postition $position")
                cardClickListener.onCardClicked(position)

            }

        }
    }
}