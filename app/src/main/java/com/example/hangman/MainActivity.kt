package com.example.hangman

import android.os.Bundle
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
//import androidx.compose.runtime.rememberimport androidx.compose.runtime.setValue
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
    onClick: () -> Unit) {
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

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val myArrayWords = stringArrayResource(id = R.array.hangman_words)
    var mysteryWord by remember { mutableStateOf(getMysteryWord(myArrayWords)) }
    var gameStarted by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (!gameStarted) {
                GameStartButton(
                    text = stringResource(R.string.let_s_start),
                    enabled = true,
                    textColor = colorScheme.primary,
                ) {
                    mysteryWord = getMysteryWord(myArrayWords)
                    gameStarted = true
                }
            } else {
                GameLayout(
                    mysteryWord = mysteryWord,
                    onNewGame = {
                        mysteryWord = getMysteryWord(myArrayWords)
                        gameStarted = false
                    }
                )
            }
        }
    }
}

private fun getMysteryWord(words: Array<String>): String {
    return words.random().uppercase()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    HangManTheme {
        MainScreen()
    }
}