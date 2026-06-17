package ir.mrghost.gamebase

import android.content.Context
import ir.mrghost.gamebase.data.local.FakeGameRepository
import ir.mrghost.gamebase.data.local.favorites.FavoritesRepository
import ir.mrghost.gamebase.data.local.GameBaseDatabase
import ir.mrghost.gamebase.data.local.GameRepository

object AppModule {
    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    private val database by lazy {
        GameBaseDatabase.getInstance(appContext)
    }

    private val gameRepository: GameRepository by lazy {
        FakeGameRepository()
    }

    private val favoritesRepository: FavoritesRepository by lazy {
        FavoritesRepository(database.favoriteDao())
    }

    fun provideGameRepository(): GameRepository = gameRepository
    fun provideFavoritesRepository(): FavoritesRepository = favoritesRepository
}