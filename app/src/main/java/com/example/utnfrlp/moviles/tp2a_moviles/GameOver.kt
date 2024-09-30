package com.example.utnfrlp.moviles.tp2a_moviles

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun GameOverScreen(score: Int, onPlayAgain: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "¡Perdiste!", style = androidx.compose.material3.MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Tu puntaje final es: $score")
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = onPlayAgain) {
            Text("Jugar de nuevo")
        }
    }
}
