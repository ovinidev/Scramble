package com.ui.theme.game

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.words.project.allWords

class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    fun updateInputValue(inputValue: String) {
        _uiState.update { it.copy(inputValue = inputValue) }
    }

    private fun clearInput() {
        _uiState.update { it.copy(inputValue = "") }
    }

    fun submit() {
        val state = _uiState.value

        if (state.inputValue.isBlank()) return

        if (state.currentRightWord == state.inputValue) {
            updateScore()
            updateSuccessWords()
            skip()
            clearInput()
        } else {
            updateErrorState(true)
        }
    }

    private fun updateSuccessWords() {
        val state = _uiState.value
        if (!state.wordsSuccess.contains(state.currentRightWord)) {
            _uiState.update {
                it.copy(wordsSuccess = it.wordsSuccess + state.currentRightWord)
            }
        }
    }

    private fun updateErrorState(isWrong: Boolean) {
        _uiState.update { it.copy(isGuessedWordWrong = isWrong) }
    }

    private fun updateScore() {
        _uiState.update { it.copy(score = it.score + 10) }
    }

    fun skip() {
        clearInput()
        updateErrorState(false)
        updateSuccessWords()
        getRandomWord()

        if (_uiState.value.currentWordCount < allWords.size) {
            _uiState.update { it.copy(currentWordCount = it.currentWordCount + 1) }
        }
    }

    fun getRandomWord() {
        val wordsSuccess = _uiState.value.wordsSuccess

        if (wordsSuccess.size == allWords.size) {
            _uiState.update { it.copy(isGameFinished = true) }
            return
        }

        val wordsToScramble = allWords.filter { wordsSuccess.contains(it).not() }
        val currentRightWord = wordsToScramble.random()
        val listWords = currentRightWord.toCharArray()
        listWords.shuffle()
        val currentScrambleWord = String(listWords)

        _uiState.update {
            it.copy(currentRightWord = currentRightWord, currentScrambleWord = currentScrambleWord)
        }
    }

    fun newGame() {
        _uiState.update {
            GameUiState(
                currentWordCount = 1,
                score = 0,
                currentRightWord = "",
                currentScrambleWord = "",
                inputValue = "",
                wordsSuccess = emptyList(),
                isGuessedWordWrong = false,
                isGameFinished = false
            )
        }
        getRandomWord()
    }
}