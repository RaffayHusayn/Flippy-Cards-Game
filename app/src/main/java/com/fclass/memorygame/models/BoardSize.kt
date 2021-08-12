package com.fclass.memorygame.models

enum class BoardSize(val numCards : Int) {
    EASY(8), //2x4 grid
    MEDIUM(18),//3x6 grid
    HARD(24);//4x6 grid

    fun getWidth() : Int{
        return when (this){
            EASY -> 2
            MEDIUM -> 3
            HARD -> 4
        }
    }

    fun getHeight() : Int {
        return numCards/getWidth()
    }

    fun getNumPairs() : Int {
        return numCards / 2
    }
}