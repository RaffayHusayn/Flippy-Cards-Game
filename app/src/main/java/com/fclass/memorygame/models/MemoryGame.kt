package com.fclass.memorygame.models

import android.util.Log
import com.fclass.memorygame.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize) {


    val cards: List<MemoryCard>
    var numPairsFound = 0
    private var indexOfSingleSelectedCard: Int? = null //type of this is nullable int
    init{

        //shuffled means random in kotlin and we take total number of pairs because we will need 12 icons for 24 grid
        val chosenImages = DEFAULT_ICONS.shuffled().take(boardSize.getNumPairs())
        //doubling up the chosen images
        val randomizedImages = (chosenImages + chosenImages).shuffled()

        //Instead of passing just the Randomized Vector Icons we want to instead pass on
        //a MemoryCard object (of a data class) where it has properties like
        //1. Identifier which is the one element of the list of randomizedImages, 2. if the card is
        //faceup or facedown, 3. if the card is matched or not
        cards = randomizedImages.map{MemoryCard(identifier = it)}
    }

    fun flipCard(position: Int): Boolean  {
        val card = cards[position]

        var foundMatch = false
        if (indexOfSingleSelectedCard== null){
            //0 or 2 cards previously flipped over
            restoreCards()
            indexOfSingleSelectedCard = position
        }
        else{
            //exactly 1 card previously flipped over
            foundMatch = checkForMatch(indexOfSingleSelectedCard!!, position)

            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
        return foundMatch
    }

    private fun checkForMatch(position1: Int, position2: Int) : Boolean{
        if (cards[position1].identifier != cards[position2].identifier){
            return false
        }

        cards[position1].isMatched = true
        cards[position2].isMatched = true
        numPairsFound ++
        return true



    }

    private fun restoreCards() {

        for (card in cards){
           if (!card.isMatched){
                card.isFaceUp = false
           }


        }
    }



    fun haveWonGame(): Boolean {
        return numPairsFound == boardSize.getNumPairs()
    }

    fun isCardFaceIp(position: Int): Boolean {
        return cards[position].isFaceUp
    }


}