package com.example.hangman.game

import com.example.hangman.R

class Game(private val mysteryWord:String) {
    private var currentGallowsState =0 //state of gallows image, used to determine which image to display
    private var currentGallowsDrawableId= R.drawable.hangman0
    private var guessWord = mysteryWord.replace(Regex("A-Z"), "_")
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
    /**
     * Checks if the input letter is in the mystery word and updates the gallows, [usedLetters],
     * and [guessWord] states accordingly.
     * @param inputLetter The letter to be check.
     */
    fun checkLetter(inputLetter: String) {
        // Add the input letter to the used letters string.
        usedLetters += "$inputLetter, "
        // Check if the mystery word contains the input letter.
        if (mysteryWord.contains(inputLetter)) {
            // Update the guess word with the input letter.
            // buildString function is used to create a new string with the updated guess word.
            guessWord = buildString {
                // we iterate over the indices of the mystery word and append the input letter
                // if the letter in mystery word at the current index is equal to the input letter,
                // otherwise we append the letter in the guess word at the current index
                // (this will be either the previously guessed letter or an underscore).
                for (i in mysteryWord.indices) {
                    append(if (mysteryWord[i].toString() == inputLetter) inputLetter else guessWord[i])
                }
            }
        } else {
            // If the mystery word does not contain the input letter, we update the gallows state.
            currentGallowsState++
            currentGallowsDrawableId = getGallowsStateDrawable()
        }
    }

    /**
     * Get the drawable id of the gallows image.
     * The [currentGallowsDrawableId] is private hence the need for the getter
     */
    fun getGallowsDrawableId() = currentGallowsDrawableId // this is a compact way of writing a function
    /**
     * Get the current [guessWord].
     */
    fun getGuessWord() = guessWord
    /**
     * Get the [usedLetters] string.
     */
    fun getUsedLetters(): String {
        return usedLetters
    } // this is a full way of writing a function
}