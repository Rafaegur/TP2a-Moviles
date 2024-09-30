import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.first

class DataStoreManager(context: Context) {

    // Inicializar DataStore
    private val Context.dataStore by preferencesDataStore(name = "high_scores_prefs")

    // Usar el contexto pasado para acceder al dataStore
    private val dataStore = context.dataStore

    private val HIGH_SCORES_KEY = stringPreferencesKey("high_scores")

    // Obtener los puntajes almacenados
    fun getHighScores(): Flow<List<Pair<String, Int>>> {
        return dataStore.data.map { preferences ->
            val highScoresString = preferences[HIGH_SCORES_KEY] ?: ""
            if (highScoresString.isEmpty()) {
                emptyList() // Si no hay puntajes, retorna lista vacía
            } else {
                highScoresString.split("|").map { entry ->
                    val (name, score) = entry.split(",")
                    name to score.toInt() // Convertir a Pair<String, Int>
                }
            }
        }
    }

    // Guardar los mejores puntajes
    suspend fun saveHighScores(newScore: List<Pair<String, Int>>) {
        // Obtener los puntajes actuales
        val currentScores = getHighScores().first()

        // Combinar puntajes actuales con el nuevo y tomar los 3 mejores
        val updatedScores = (currentScores + newScore)
            .sortedByDescending { it.second } // Ordenar por puntaje
            .take(3) // Mantener solo los 3 mejores

        // Guardar los puntajes actualizados en DataStore
        dataStore.edit { preferences -> // Usa dataStore directamente
            val serializedScores = updatedScores.joinToString(separator = "|") { "${it.first},${it.second}" }
            preferences[HIGH_SCORES_KEY] = serializedScores
        }
    }

}


