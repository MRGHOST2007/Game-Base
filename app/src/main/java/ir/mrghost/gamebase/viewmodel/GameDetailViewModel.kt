package ir.mrghost.gamebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.mrghost.gamebase.AppModule
import ir.mrghost.gamebase.data.Game
import ir.mrghost.gamebase.data.GameRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class GameDetailViewModel(
    private val repository: GameRepository = AppModule.provideGameRepository()
) : ViewModel() {
    fun getGameById(gameId: Long): StateFlow<Game?> = flow {
        val allGames = repository.getAllGames()
        emit(allGames.find { it.id == gameId })
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = null
    )
}