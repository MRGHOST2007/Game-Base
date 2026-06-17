package ir.mrghost.gamebase.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.mrghost.gamebase.data.local.favorites.FavoriteDAO
import ir.mrghost.gamebase.data.local.favorites.FavoriteEntity
import ir.mrghost.gamebase.data.local.games.Game
import ir.mrghost.gamebase.data.local.games.GameDAO

@Database(
    entities = [Game::class,FavoriteEntity::class],
    version = 2,
    exportSchema = false
)
abstract class GameBaseDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDAO
    abstract fun gameDao() : GameDAO

    companion object {
        @Volatile
        private var INSTANCE: GameBaseDatabase? = null

        fun getInstance(context: Context): GameBaseDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GameBaseDatabase::class.java,
                    "gamebase_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}