package vini.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CustomColor(
    val primary: Color = Color(0xFFB01078),
    val secondary: Color = Color(0xFF03DAC6),
)

val LocalColor = staticCompositionLocalOf { CustomColor() }

object CustomTheme {
    val color: CustomColor
        @Composable
        @ReadOnlyComposable
        get() = LocalColor.current
}

@Composable
fun CustomTheme(
    color: CustomColor = CustomTheme.color,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalColor provides color
    ) {
        content()
    }
}
