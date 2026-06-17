package ir.mrghost.gamebase.data.local.games

import ir.mrghost.gamebase.data.local.GameRepository
import ir.mrghost.gamebase.utils.GameGenre
import kotlinx.coroutines.flow.firstOrNull

class GamesRepository(
    private val gameDao: GameDAO
) : GameRepository {
    override suspend fun getAllGames(): List<Game> =
        gameDao.getAllGames().firstOrNull() ?: emptyList()


    override suspend fun getGameAwards(): List<Game> {
        val allGames = getAllGames()
        return listOf(
            allGames.lastOrNull(),
            allGames.getOrNull(2),
            allGames.getOrNull(13),
            allGames.getOrNull(14)
        ).filterNotNull()
    }

    override suspend fun getTopPagerGames(): List<Game> {
        val allGames = getAllGames()
        return listOf(
            allGames.randomOrNull(),
            allGames.randomOrNull(),
            allGames.randomOrNull()
        ).filterNotNull()
    }

    override suspend fun getGamesByGenre(genre: GameGenre): List<Game> =
        gameDao.getGamesByGenre(genre).firstOrNull() ?: emptyList()


}