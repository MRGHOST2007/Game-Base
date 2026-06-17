package ir.mrghost.gamebase.data.local.games

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.mrghost.gamebase.utils.GameGenre
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGames(games: List<Game>)

    @Query("SELECT * FROM games")
    fun getAllGames(): Flow<List<Game>>

    @Query("SELECT * FROM games WHERE id = :gameId")
    suspend fun getGameById(gameId: Long): Game?

    @Query("SELECT * FROM games WHERE genre = :genre")
    fun getGamesByGenre(genre: GameGenre): Flow<List<Game>>

    @Query("SELECT * FROM games WHERE title LIKE '%' || :query || '%'")
    fun searchGames(query: String): Flow<List<Game>>
}