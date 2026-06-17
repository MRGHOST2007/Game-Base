package ir.mrghost.gamebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.mrghost.gamebase.AppModule
import ir.mrghost.gamebase.data.local.games.Game
import ir.mrghost.gamebase.data.local.GameRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameDetailViewModel(
    private val repository: GameRepository = AppModule.provideGameRepository()
) : ViewModel() {
    private val favoritesRepository = AppModule.provideFavoritesRepository()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite: StateFlow<Boolean> = _isFavorite.asStateFlow()

    private val _game = MutableStateFlow<Game?>(null)
    val game: StateFlow<Game?> = _game.asStateFlow()

    fun loadGame(gameId: Long) {
        viewModelScope.launch {
            val loadedGame = repository.getAllGames().find { it.id == gameId }
            _game.value = loadedGame

            loadedGame?.let {
                _isFavorite.value = favoritesRepository.isFavorite(it.id)
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            _game.value?.let { game ->
                val newState = favoritesRepository.toggleFavorite(game)
                _isFavorite.value = newState
            }
        }
    }
}