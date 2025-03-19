package com.example.hangman.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.content.MediaType
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
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
            text= stringResource(R.string.used_letters),
            textAlign = TextAlign.Left,
            fontWeight = FontWeight.Bold,
            color = colorScheme.primary,
            modifier = Modifier.fillMaxWidth()

        )
    }
}