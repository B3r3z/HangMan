package com.example.hangman

import android.os.Bundle
import android.service.autofill.OnClickAction
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
import com.example.hangman.game.LetterInputField
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
fun GameStartButton(text: String, enabled: Boolean, textColor: Color, modifier: Modifier = Modifier, onClick:() ->Unit){
    TextButton(onClick = onClick, enabled=enabled) {
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
    var mysteryWord =""
    // The Scaffold is a "canvas" for the screen. It provides a surface for the content.
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        // The Column is a composable that places its children in a vertical sequence.
        Column(
            horizontalAlignment = Alignment.CenterHorizontally, // Center the children horizontally
            verticalArrangement = Arrangement.Center, // Center the children vertically
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize() // Fill the whole screen
        ) {
        }
        GameStartButton(
            text = stringResource(R.string.let_s_start),
            enabled = true,
            textColor = colorScheme.primary,
        ) {
            // NOTE: This is the trailing lambda of the GameStartButton composable that is called
            // when the button is clicked. Basically it's the onClick argument of the
            // GameStartButton composable function
            mysteryWord = getMysteryWord(words = myArrayWords)
            Log.d("MainActivity", "Mystery word: $mysteryWord")
        }
        GameLayout(
            mysteryWord = mysteryWord,
        )
    }
}

private fun getMysteryWord(words: Array<String>): String { // Accepts words array
    return words.random().uppercase()
}


@Preview
@Composable
fun MainScreenPreview() {
    HangManTheme{
        MainScreen()
    }
}
