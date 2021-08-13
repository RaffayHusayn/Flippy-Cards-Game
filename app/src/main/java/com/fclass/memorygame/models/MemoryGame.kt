package com.fclass.memorygame.models

import com.fclass.memorygame.utils.DEFAULT_ICONS

class MemoryGame(private val boardSize: BoardSize) {

        val cards: List<MemoryCard>
        val numPairsFound = 0

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


}