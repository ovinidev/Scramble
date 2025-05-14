package com.ui.theme.scramble.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.words.project.allWords

@Composable
fun GameScreen() {
    val gameViewModel = viewModel<GameViewModel>()
    val gameUiState by gameViewModel.uiState.collectAsState()

    val isGameFinished = gameUiState.isGameFinished
    val isLastRound = gameUiState.currentWordCount == allWords.size

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        if (!isGameFinished) {
            Text(
                text = "Word ${gameUiState.currentWordCount} / ${allWords.size}",
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = gameUiState.currentScrambleWord,
                style = MaterialTheme.typography.h4.copy(fontSize = 36.sp),
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (!gameUiState.isGameFinished) {
            TextField(
                value = gameUiState.inputValue,
                onValueChange = { value -> gameViewModel.updateInputValue(value) },
                placeholder = {
                    Text(text = "Enter the word", style = MaterialTheme.typography.body1)
                },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { gameViewModel.submit() }),
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = MaterialTheme.colors.surface,
                    focusedIndicatorColor = MaterialTheme.colors.primary,
                    unfocusedIndicatorColor = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                )
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isGameFinished) {
            Text(
                text = "Congratulations! You finished the game!",
                style = MaterialTheme.typography.h6,
                color = MaterialTheme.colors.primary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { gameViewModel.newGame() }) {
                Text(text = "New Game")
            }
        } else {
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = { gameViewModel.submit() },
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "Submit")
                }

                Spacer(modifier = Modifier.width(16.dp))

                OutlinedButton(
                    onClick = { gameViewModel.skip() },
                    modifier = Modifier.weight(1f),
                    enabled = !isLastRound
                ) {
                    Text(text = "Skip")
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (gameUiState.isGuessedWordWrong) {
            Text(
                text = "Wrong word! Try again!",
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.error
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Score(score = gameUiState.score)
    }
}

@Composable
fun Score(score: Int) {
    Text(
        text = "Score: $score",
        style = MaterialTheme.typography.h5,
        color = MaterialTheme.colors.primary
    )
}