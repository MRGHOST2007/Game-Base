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

    private val repository: GameRepository by lazy {
        FakeGameRepository()
    }

    val favoritesRepository: FavoritesRepository by lazy {
        FavoritesRepository(database.favoriteDao())
    }

    fun provideGameRepository(): GameRepository = repository
    fun provideFavoritesRepository(): FavoritesRepository = favoritesRepository
}