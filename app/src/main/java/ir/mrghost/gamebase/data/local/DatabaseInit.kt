package ir.mrghost.gamebase.data.local

import ir.mrghost.gamebase.data.local.games.Game
import ir.mrghost.gamebase.data.local.games.GameDAO
import kotlinx.coroutines.flow.firstOrNull

class DatabaseInit(
    private val gameDao: GameDAO
) {
    suspend fun initializeGames(games: List<Game>) {
        val existingGames = gameDao.getAllGames().firstOrNull()
        if (existingGames.isNullOrEmpty()) {
            gameDao.insertGames(games)
        }
    }
}