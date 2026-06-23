package ir.mrghost.gamebase.data.local

import android.os.Parcelable
import androidx.annotation.DrawableRes
import ir.mrghost.gamebase.utils.GameGenre
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
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