package com.example.hangman.game

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hangman.R
import org.w3c.dom.Text

/**
* a composable function that display the gallows img
*
*
*/
@Composable
private fun GallowsImage(resId:Int, modifier: Modifier = Modifier, tint: Color = Color.Black){
    Image(
        painter = painterResource(id = resId),
        contentDescription = null,
        colorFilter = ColorFilter.tint(tint),
        modifier = modifier.fillMaxSize()
    )
}
/*
*composable fun that displays current state of the guessed word
 */
@Composable
private fun GuessWordText(word: String, modifier: Modifier=Modifier){
    Text(
        text =word.uppercase(),
        textAlign =TextAlign.Center,
        fontWeight = FontWeight.Bold,
        letterSpacing = 8.sp,
        modifier=modifier.fillMaxWidth()
    )
}
/*
*Composable fun, displays the used letters string with a label
 */
@Composable
private fun UsedLettersText(usedLetters: String, modifier: Modifier){
    Column {
        Text(
            //text= stringResource(R.string.used_letters),
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
/**
 * A composable function that displays a TextField with a Button. Used to input letters.
 * @param textFieldValue The value to e displayed in the TextField.
 * @param buttonText The text to be displayed on the Button.
 * @param buttonEnabled Whether the Button is enabled or not.
 * @param isError Whether the TextField is in an error state or not.
 * @param onButtonClick The callback to be called when the Button is clicked.
 * @param onValueChange The callback to be called when the TextField value changes.
 * @param modifier The modifier to apply to the Row composable.
 */
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
    // The LetterInputField composable is a Row that contains a TextField and a Button.
    // The Row is a container that lays out its children in a horizontal line.
    Row(
        modifier = modifier.fillMaxWidth(), // The Row fills the available width
        horizontalArrangement = Arrangement.Center, // The children are centered horizontally
    ) {
        TextField(
            // The value of the TextField. The label is displayed when the value
            // is empty and not in focus.
            value = textFieldValue,
            // The callback that is called when the value changes.
            onValueChange = onValueChange,
            singleLine = true,
            // The shape can be modified.
            shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp),
            // hint for the user what should be input in the TextField.
            label = { Text(text = stringResource(R.string.enter_letter)) },
            isError = isError
        )
        // The Button is displayed to the right of the TextField.
        Button(
            onClick = onButtonClick, // The callback that is called when the Button is clicked.
            enabled = buttonEnabled, // The Button can be enabled/disabled.
            shape = RoundedCornerShape(
                topStart = 0.dp,
                topEnd = 8.dp,
                bottomStart = 0.dp,
                bottomEnd = 8.dp
            ), // we can modify the shape of the Button.
            modifier = Modifier
                .defaultMinSize(minHeight = TextFieldDefaults.MinHeight) // Match TextField height
        ) {
            Text(text = buttonText)
        }
    }
}

/**
 * Composable function that displays the game layout.
 * Combines all of the game related composable functions.
 * @param mysteryWord The word to be guessed.
 * It is used to notify the parent composable that the game has ended.
 * @param modifier The modifier to apply to the composable.
 */
@Composable
fun GameLayout(
    mysteryWord: String,
    modifier: Modifier = Modifier
) {
    // The inputLetter is a mutable state that holds the value of the TextField.
    // It is lifted outside of the LetterInputField composable to maintain its state.
    // This is so called state hoisting.
    var inputLetter by remember { mutableStateOf("") }
    var isErrorState by remember { mutableStateOf(false) }
    // The game is a Game object that holds the game state and logic.
    val game = remember { Game(mysteryWord) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
    ) {
        GallowsImage(resId = game.getGallowsDrawableId(), tint = colorScheme.primary)
        GuessWordText(
            word = game.getGuessWord(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        LetterInputField(
            textFieldValue = inputLetter,
            buttonText = stringResource(R.string.check_letter),
            onButtonClick = {
                game.checkLetter(inputLetter)
                inputLetter=""
            },
            onValueChange = {
                //When the user enters the valu its converdet to uppercase and stored in the inputLetter
                inputLetter = it.uppercase()
            },
            isError = false,
            buttonEnabled = true
        )
        UsedLettersText(
            usedLetters = game.getUsedLetters(),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
        )
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device  = "id:pixel_8_pro"
)
@Composable
fun GameLayoutPreview(){
    GameLayout(mysteryWord = "TEST")
}