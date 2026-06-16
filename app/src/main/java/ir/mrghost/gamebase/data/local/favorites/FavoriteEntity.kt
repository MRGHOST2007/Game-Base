package ir.mrghost.gamebase.data.local.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.mrghost.gamebase.data.local.Game
import ir.mrghost.gamebase.utils.GameGenre

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey
    val gameId: Long,
    val title: String,
    val image: Int,
    val rating: Float,
    val addedTimestamp: Long = System.currentTimeMillis()
) {

    fun toGame(): Game {
        return Game(
            id = gameId,
            title = title,
            releaseYear = 0,
            developer = "",
            genre = GameGenre.Action,
            description = "",
            rating = rating,
            price = 0f,
            image = image
        )
    }

    companion object {
        fun fromGame(game: Game): FavoriteEntity {
            return FavoriteEntity(
                gameId = game.id,
                title = game.title,
                image = game.image,
                rating = game.rating
            )
        }
    }
}