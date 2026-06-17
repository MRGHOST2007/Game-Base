package ir.mrghost.gamebase.data.local.games

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.mrghost.gamebase.utils.GameGenre
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "games")
data class Game(
    @PrimaryKey
    val id: Long,
    val title: String,
    val releaseYear: Int,
    val developer: String,
    val genre: GameGenre,
    val description: String,
    val rating: Float,
    val price: Float,
    @DrawableRes val image: Int
) : Parcelable