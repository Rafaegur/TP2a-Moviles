package com.example.utnfrlp.moviles.tp2a_moviles

import DataStoreManager
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import kotlin.random.Random
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun GuessNumberScreen(navController: NavController, dataStoreManager: DataStoreManager) {
    // Variables del juego
    var userInput by remember { mutableStateOf(TextFieldValue("")) }
    var userName by remember { mutableStateOf(TextFieldValue("")) }
    var randomNumber by remember { mutableStateOf(Random.nextInt(1, 5)) }
    var triesLeft by remember { mutableStateOf(5) }
    var score by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }
    var isGameOver by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Adivina el número entre 1 y 5", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(16.dp))
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Tu nombre") },
            modifier = Modifier.fillMaxWidth()
        )
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
            if (guessedNumber != null && (guessedNumber == 1 || guessedNumber == 2 || guessedNumber == 3 || guessedNumber == 4 || guessedNumber == 5)) {
                if (guessedNumber == randomNumber) {
                    score += 10
                    message = "¡Adivinaste!"
                    randomNumber = Random.nextInt(1, 5)
                } else {
                    triesLeft--
                    message = "Fallaste. Te quedan $triesLeft intentos."
                    if (triesLeft == 0) {
                        isGameOver = true
                    }
                }
            } else {
                message = "Introduce un número válido. Debe estar entre 1 y 5"
            }
        }) {
            Text("Ingresar")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Puntaje: $score")
        Text(text = message)
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // Comparar y guardar el puntaje si es necesario
            coroutineScope.launch {
                dataStoreManager.getHighScores().collect { savedScores ->
                    if (savedScores.isNullOrEmpty() || score > savedScores.maxOf { it.second }) {
                        dataStoreManager.saveHighScores(
                            listOf(userName.text to score)
                        )
                    }
                }
            }
            navController.navigate("highScores")
        }) {
            Text("Ver Mejores Puntajes")
        }
    }

    if (isGameOver) {
        GameOverScreen(score = score) {

            // Comparar y guardar el puntaje si es necesario
            coroutineScope.launch {
                dataStoreManager.getHighScores().collect { savedScores ->
                    if (savedScores.isNullOrEmpty() || score > savedScores.maxOf { it.second }) {
                        dataStoreManager.saveHighScores(
                            listOf(userName.text to score)
                        )
                    }
                }
            }


            // Reiniciar el juego
            userInput = TextFieldValue("")
            randomNumber = Random.nextInt(1, 5)
            triesLeft = 5
            score = 0
            message = ""
            isGameOver = false
        }
    }
}



