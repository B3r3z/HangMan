package com.example.hangman.game

import com.example.hangman.R

class Game(private val mysteryWord:String) {
    private var currentGallowsState =0 //state of gallows image, used to determine which image to display
    private var currentGallowsDrawableId= R.drawable.hangman0
    private var gussWord = mysteryWord.replace(Regex("A-Z"), "_")
    private var usedLetters =""


    private fun getGallowsStateDrawable(): Int{
        return when (currentGallowsState){//when works like switch
            0->R.drawable.hangman0
            1->R.drawable.hangman1
            2->R.drawable.hangman2
            3->R.drawable.hangman3
            4->R.drawable.hangman4
            5->R.drawable.hangman5
            6->R.drawable.hangman6
            7->R.drawable.hangman7
            8->R.drawable.hangman8
            9->R.drawable.hangman9
            else ->-1
        }
    }
}