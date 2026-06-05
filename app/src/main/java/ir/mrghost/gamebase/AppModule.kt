package ir.mrghost.gamebase

import ir.mrghost.gamebase.data.FakeGameRepository
import ir.mrghost.gamebase.data.GameRepository

object AppModule {
    private val repository : GameRepository by lazy {
        FakeGameRepository()
    }

    fun provideGameRepository(): GameRepository = repository
}