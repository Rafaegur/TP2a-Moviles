package com.example.utnfrlp.moviles.tp2a_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessNumberScreen()
        }
    }
}

@Composable
fun GuessNumberScreen() {
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var randomNumber by remember { mutableStateOf(Random.nextInt(1, 5)) }
    var triesLeft by remember { mutableStateOf(5) }
    var score by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Adivina el número entre 1 y 5", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = userInput,
            onValueChange = { userInput = it },
            label = { Text("Tu número") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            val guessedNumber = userInput.text.toIntOrNull()
            if (guessedNumber != null) {
                if (guessedNumber == randomNumber) {
                    score += 10
                    message = "¡Adivinaste!"
                } else {
                    triesLeft--
                    message = "Fallaste. Te quedan $triesLeft intentos."
                    if (triesLeft == 0) {
                        score = 0
                        message = "Perdiste. El número era $randomNumber."
                        triesLeft = 5
                        randomNumber = Random.nextInt(1, 5)
                    }
                }
            } else {
                message = "Introduce un número válido."
            }
        }) {
            Text("Ingresar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Puntaje: $score")
        Text(text = message)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGuessNumberScreen() {
    GuessNumberScreen()
}

