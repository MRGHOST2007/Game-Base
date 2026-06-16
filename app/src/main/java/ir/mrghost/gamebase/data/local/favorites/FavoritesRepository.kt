package ir.mrghost.gamebase.data.local.favorites

import ir.mrghost.gamebase.data.local.Game
import kotlinx.coroutines.flow.Flow

class FavoritesRepository (
    private val favoriteDao: FavoriteDAO
){
    fun getAllFavorites(): Flow<List<FavoriteEntity>> {
        return favoriteDao.getAllFavorites()
    }

    suspend fun addFavorite(game: Game) {
        val entity = FavoriteEntity.fromGame(game)
        favoriteDao.insertFavorite(entity)
    }

    suspend fun removeFavorite(gameId: Long) {
        favoriteDao.deleteFavoriteById(gameId)
    }

    suspend fun isFavorite(gameId: Long): Boolean {
        return favoriteDao.isFavorite(gameId)
    }

    suspend fun toggleFavorite(game: Game): Boolean {
        return if (isFavorite(game.id)) {
            removeFavorite(game.id)
            false
        } else {
            addFavorite(game)
            true
        }
    }
}