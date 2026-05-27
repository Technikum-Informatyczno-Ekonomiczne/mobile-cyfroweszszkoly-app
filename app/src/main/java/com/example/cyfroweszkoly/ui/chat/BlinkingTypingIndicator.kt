package com.example.cyfroweszkoly.ui.chat

import androidx.compose.ui.graphics.Color
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.room.util.copy

@Composable
fun BlinkingTypingIndicator(color: Color) {
    // Tworzymy nieskończoną pętlę animacji
    val infiniteTransition = rememberInfiniteTransition(label = "typing_anim")

    // Animujemy przezroczystość (alpha) od 0.3 (mocno wyblakłe) do 1.0 (pełny kolor)
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse // Odbija się tam i z powrotem
        ),
        label = "alpha_anim"
    )

    // Nakładamy wyliczoną przezroczystość na tekst
    Text(
        text = "Myślę...",
        modifier = Modifier.alpha(alpha),
        color = color.copy(alpha = alpha),
        style = MaterialTheme.typography.bodyMedium
    )
}