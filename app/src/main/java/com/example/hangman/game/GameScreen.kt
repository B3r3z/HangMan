package com.example.hangman.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hangman.R
import com.example.hangman.ui.theme.HangManTheme


@Composable
private fun GallowsImage(resId: Int, modifier: Modifier = Modifier, tint: Color = Color.Black) {
    Image(
        painter = painterResource(id = resId),
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
        modifier = modifier.fillMaxWidth().aspectRatio(1f)
    )
}

@Composable
private fun GuessWordText(word: String, modifier: Modifier = Modifier) {
    Text(
        text = word.uppercase(),
        textAlign = TextAlign.Center,
        fontWeight = FontWeight.Bold,
        letterSpacing = 8.sp,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
private fun UsedLettersText(usedLetters: String, modifier: Modifier) {
    Column {
        Text(
            text = stringResource(R.string.used_letters),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = colorScheme.primary,
            modifier = modifier.fillMaxWidth()
        )
        Text(
            text = usedLetters.uppercase(),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = colorScheme.error,
            modifier = modifier.fillMaxWidth()
        )
    }
}

@Composable
fun LetterInputField(
    textFieldValue: String,
    buttonText: String,
    buttonEnabled: Boolean,
    isError: Boolean,
    onButtonClick: () -> Unit,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // Bez remember { mutableStateOf(...) }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        TextField(
            value = textFieldValue,
            onValueChange = onValueChange,  // wywołaj callback rodzica
            singleLine = true,
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
            label = { Text(text = stringResource(R.string.enter_letter)) },
            isError = isError
        )
        Button(
            onClick = onButtonClick,
            enabled = buttonEnabled,
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 8.dp,
                bottomStart = 0.dp,
                bottomEnd = 8.dp
            ),
            modifier = Modifier
                .defaultMinSize(minHeight = TextFieldDefaults.MinHeight)
        ) {
            Text(text = buttonText)
        }
    }
}

@Composable
fun GameLayout(
    mysteryWord: String,
    modifier: Modifier = Modifier,
    //onNewGame: () -> Unit
    onEnd:(GameStatus) ->Unit
) {
    var inputLetter by remember { mutableStateOf("") }
    var isErrorState by remember { mutableStateOf(false) }
    val game = remember { Game(mysteryWord) }
    var guessWord by remember { mutableStateOf(game.guessWord) }
    var usedLetters by remember { mutableStateOf(game.usedLetters) }
    var gallowsDrawableId by remember { mutableIntStateOf(game.currentGallowsDrawableId) }

    Column(
        modifier = modifier.padding(16.dp).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        GallowsImage(resId = gallowsDrawableId, tint = colorScheme.primary)
        GuessWordText(
            word = guessWord,
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        )
        LetterInputField(
            textFieldValue = inputLetter,
            buttonText = stringResource(R.string.check_letter),
            onButtonClick = {
                if (inputLetter.isNotEmpty()) {
                    game.checkLetter(inputLetter)
                    guessWord = game.guessWord
                    usedLetters = game.usedLetters
                    gallowsDrawableId = game.currentGallowsDrawableId
                    inputLetter = ""

                    // check if the game is finished
                    if(game.isGameFinished()){
                        onEnd(game.getGameStatus())
                    }
                }
            },
            onValueChange = {
                if (it.length <= 1) {
                    inputLetter = it.filter { it.isLetter() }.uppercase()
                    isErrorState = false
                } else {
                    isErrorState = true
                }
            },
            isError = isErrorState,
            buttonEnabled = true
        )
        UsedLettersText(
            usedLetters = usedLetters,
            modifier = Modifier.padding(top = 16.dp).fillMaxWidth()
        )

    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = "id:pixel_8_pro"
)
@Composable
fun GameLayoutPreview() {
    //GameLayout(mysteryWord = "TEST", onNewGame = {})
    HangManTheme {
        GameLayout(mysteryWord = "TEST", onEnd = {})
    }
}