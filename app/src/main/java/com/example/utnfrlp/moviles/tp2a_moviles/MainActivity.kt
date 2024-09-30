package com.example.utnfrlp.moviles.tp2a_moviles

import DataStoreManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var dataStoreManager: DataStoreManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataStoreManager = DataStoreManager(this) // Inicializa DataStoreManager

        setContent {
            val navController = rememberNavController()
            AppNavigation(navController = navController, dataStoreManager = dataStoreManager)
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, dataStoreManager: DataStoreManager) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            GuessNumberScreen(navController = navController, dataStoreManager = dataStoreManager)
        }
        composable("highScores") {
            HighScoresScreen(navController = navController, dataStoreManager = dataStoreManager)
        }
        composable("gameOver/{score}") { backStackEntry ->
            val score = backStackEntry.arguments?.getString("score")?.toIntOrNull() ?: 0
            GameOverScreen(score = score, onPlayAgain = {
                navController.navigate("home") // Volver a la pantalla de inicio
            })
        }
    }
}


