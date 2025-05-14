package com.ui.theme.scramble.ui

data class GameUiState(
    val score: Int = 0,
    val currentWordCount: Int = 1,
    val currentScrambleWord: String = "",
    val currentRightWord: String = "",
    val inputValue: String = "",
    val isGuessedWordWrong: Boolean = false,
    val wordsSuccess: List<String> = emptyList(),
    val isGameFinished: Boolean = false
)