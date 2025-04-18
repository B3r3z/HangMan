package com.example.hangman

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hangman.game.GameLayout
import com.example.hangman.game.GameStatus
import com.example.hangman.ui.theme.HangManTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HangManTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun GameStartButton(
    text: String,
    enabled: Boolean,
    textColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick, enabled = enabled) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = if (enabled) textColor else textColor.copy(alpha = 0.5F),
            fontFamily = FontFamily.Cursive,
            fontSize = 36.sp,
            modifier = Modifier
                .padding(16.dp)
        )
    }
}
/**
 * The MainScreen composable is the entry point of the app.
 * It displays the game screen and related UI elements.
 */
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val wordsArray = stringArrayResource(id = R.array.hangman_words)
    // The game state is a mutable state that can be changed. The remember function
    // ensures that the value is retained across recompositions.
    var gameStatus: GameStatus by remember { mutableStateOf(GameStatus.NOT_STARTED) }

    // The mysteryWord holds the word that the player needs to guess. It is exposed to
    // the whole screen so that the word can be displayed in the snackbar when the game ends.
    var mysteryWord by remember { mutableStateOf("") }
    // The Scaffold is a "canvas" for the screen. It provides a surface for the content.
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        // The Column is a composable that places its children in a vertical sequence.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Center the children horizontally
            verticalArrangement = Arrangement.Center, // Center the children vertically
            modifier = Modifier.padding(innerPadding)
                .fillMaxSize() // Fill the whole screen
        ) {
            Log.d("MainScreen", "MainScreenComposable (${gameStatus}): $mysteryWord")
            GameStartButton(
                text = stringResource(R.string.start_text), // Get the text from the resources
                enabled = gameStatus == GameStatus.NOT_STARTED,
                textColor = colorScheme.primary,
            ) {
                // Change the game state to STARTED if it is NOT_STARTED.
                if (gameStatus == GameStatus.NOT_STARTED) {
                    mysteryWord = getMysteryWord(wordsArray)
                    // gameState is a mutable state so it will trigger
                    // a recomposition of the MainScreen composable.
                    gameStatus = GameStatus.STARTED

                }
            }
            if (gameStatus != GameStatus.NOT_STARTED) {
                // Display other elements of the  MainScreen when
                // the gameState changes from NOT_STARTED.
                when (gameStatus) {
                    GameStatus.STARTED -> {
                        //mysteryWord = getMysteryWord(wordsArray)
                        GameLayout(
                            mysteryWord = mysteryWord,
                            onEnd = {gameStatus=it }
                        )
                    }

                    GameStatus.FINISHED_WON, GameStatus.FINISHED_LOST ->{
                        GameResultScreen(
                            gameStatus = gameStatus,
                            mysteryWord = mysteryWord,
                            onPlayAgain = {
                                // Reset the game state to NOT_STARTED
                                mysteryWord = getMysteryWord(wordsArray)
                                gameStatus = GameStatus.STARTED
                            }
                        )
                    }

                    else -> {/*do nothign*/                  }
                }
            }
        }
    }
}

private fun getMysteryWord(words: Array<String>): String {
    return words.random().uppercase()
}

@Composable
fun GameResultScreen(
    gameStatus: GameStatus,
    mysteryWord: String,
    onPlayAgain: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(16.dp).fillMaxSize()
    ){
        val message = when(gameStatus){
            GameStatus.FINISHED_WON -> stringResource(R.string.you_won, mysteryWord)
            GameStatus.FINISHED_LOST -> stringResource(R.string.you_lost, mysteryWord)
            else -> ""
        }
        Text(
            text = message,
            fontSize = 24.sp,
            color = colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text=stringResource(R.string.mystery_word_was,mysteryWord),
            fontSize = 24.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )
        GameStartButton(
            text = stringResource(R.string.play_again),
            enabled = true,
            textColor = colorScheme.primary,
            onClick = onPlayAgain)
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    HangManTheme {
        MainScreen()
    }
}