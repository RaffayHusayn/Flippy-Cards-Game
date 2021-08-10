package com.fclass.memorygame

import  android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.min


class MemoryBoardAdapter(private val context: Context, private val numPieces: Int) :
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





   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

       val cardWidth = parent.width / 2 - (2*MARGIN_SIZE)//hardcoding for 2 columns, parent is the RecyclerView
       val cardHeight  = parent.height / 4 - (2*MARGIN_SIZE)//hardcoding for 4 rows, parent is the RecyclerView
       //to make the card square, just take the smaller measurement
       val cardSideLength = min(cardWidth, cardHeight)

       val view = LayoutInflater.from( context).inflate(R.layout.memory_card, parent, false)


       //grabbing out the cardView from the view that we have inflated in LayoutInflater
       val layoutParams = view.findViewById<CardView>(R.id.cardView).layoutParams as ViewGroup.MarginLayoutParams
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

    override fun getItemCount() = numPieces


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val imageButton = itemView.findViewById<ImageButton>(R.id.imageButton)
        fun bind(position: Int) {
            imageButton.setOnClickListener{
                Log.i(TAG, "clicked on postition $position")
            }

        }
    }
}