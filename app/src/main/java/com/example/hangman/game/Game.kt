package com.example.hangman.game

import com.example.hangman.R

class Game(private var mysteryWord: String) {
    private var currentGallowsState = 0
    var currentGallowsDrawableId = R.drawable.hangman0
        private set
    var guessWord = mysteryWord.replace(Regex("[A-Z]"), "_")
        private set
    var usedLetters = ""
        private set

    private fun getGallowsStateDrawable(): Int {
        return when (currentGallowsState) {
            0 -> R.drawable.hangman1
            1 -> R.drawable.hangman1
            2 -> R.drawable.hangman2
            3 -> R.drawable.hangman3
            4 -> R.drawable.hangman4
            5 -> R.drawable.hangman5
            6 -> R.drawable.hangman6
            7 -> R.drawable.hangman7
            8 -> R.drawable.hangman8
            9 -> R.drawable.hangman9
            else -> -1
        }
    }

    /**
     * Checks if the input letter is in the mystery word and updates the gallows, [usedLetters],
     * and [guessWord] states accordingly.
     * @param inputLetter The letter to be check.
     */
    fun checkLetter(inputLetter: String) {
        if (inputLetter.length != 1 || !inputLetter.all { it.isLetter() }) {
            return
        }
        val letter = inputLetter.uppercase()
        if (usedLetters.contains(letter)) {
            return
        }
        usedLetters += "$letter, "
        if (mysteryWord.contains(letter)) {
            guessWord = buildString {
                for (i in mysteryWord.indices) {
                    append(if (mysteryWord[i].toString() == letter) letter else guessWord[i])
                }
            }
        } else {
            currentGallowsState++
            currentGallowsDrawableId = getGallowsStateDrawable()
        }
    }

    fun resetGame(newMysteryWord: String) {
        mysteryWord = newMysteryWord
        currentGallowsState = 0
        currentGallowsDrawableId = R.drawable.hangman0
        guessWord = mysteryWord.replace(Regex("[A-Z]"), "_")
        usedLetters = ""
    }
}