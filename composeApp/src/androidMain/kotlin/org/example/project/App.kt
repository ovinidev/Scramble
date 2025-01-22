package org.example.project

import androidx.compose.runtime.Composable
import com.ui.theme.game.GameScreen
import vini.theme.CustomTheme


@Composable
fun App() {

    CustomTheme(color = CustomTheme.color) {
        GameScreen()

    }


}
