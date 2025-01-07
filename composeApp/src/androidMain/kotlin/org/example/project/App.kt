package org.example.project

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.delay
import androidx.compose.runtime.Composable

@Composable
fun App() {
    var buttonColor by remember { mutableStateOf(Color.Red) }
    var showAnotherButton by remember { mutableStateOf(false) }
    var count by remember { mutableIntStateOf(0) }

    fun toggleColor(color: Color) {
        buttonColor = if (color == Color.Red) Color.Blue else Color.Red
    }

    var randomText by remember { mutableStateOf("Loading...") }
    LaunchedEffect(count) {
        delay(2000)
        randomText = generateRandomText()
    }
    MaterialTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextField(value = randomText, onValueChange = { randomText = it })
            Button(
                onClick = {
                    count += 1
                    toggleColor(buttonColor)
                    randomText = generateRandomText()
                    showAnotherButton = !showAnotherButton
                },
                colors = androidx.compose.material.ButtonDefaults.buttonColors(backgroundColor = buttonColor)
            ) {
                Text(text = "Click Me", color = Color.White)
            }
            Text(
                text = count.toString(),
            )
            Text(
                text = randomText,
                style = MaterialTheme.typography.body1
            )
            if (showAnotherButton) {
                Button(onClick = { /*TODO*/ }) {
                    Text("Another Button")
                }
            }
        }
    }
}

fun generateRandomText(): String {
    val words = listOf("Compose", "Kotlin", "Jetpack", "Android", "Code", "Design", "UI")
    return words.random()
}
