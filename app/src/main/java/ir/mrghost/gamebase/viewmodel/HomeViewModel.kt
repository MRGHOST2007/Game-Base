package ir.mrghost.gamebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.mrghost.gamebase.AppModule
import ir.mrghost.gamebase.data.local.games.Game
import ir.mrghost.gamebase.data.local.GameRepository
import ir.mrghost.gamebase.utils.GameGenre
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repository: GameRepository = AppModule.provideGameRepository()
) : ViewModel() {
    private val _topPagerGames = MutableStateFlow<List<Game>>(emptyList())
    val topPagerGames: StateFlow<List<Game>> = _topPagerGames.asStateFlow()

    private val _gameAwards = MutableStateFlow<List<Game>>(emptyList())
    val gameAwards: StateFlow<List<Game>> = _gameAwards.asStateFlow()

    private val _actionGames = MutableStateFlow<List<Game>>(emptyList())
    val actionGames: StateFlow<List<Game>> = _actionGames.asStateFlow()

    private val _platformerGames = MutableStateFlow<List<Game>>(emptyList())
    val platformerGames: StateFlow<List<Game>> = _platformerGames.asStateFlow()

    private val _rpgGames = MutableStateFlow<List<Game>>(emptyList())
    val rpgGames: StateFlow<List<Game>> = _rpgGames.asStateFlow()

    init {
        viewModelScope.launch {
            _topPagerGames.value = repository.getTopPagerGames()
            _gameAwards.value = repository.getGameAwards()
            _actionGames.value = repository.getGamesByGenre(GameGenre.Action)
            _platformerGames.value = repository.getGamesByGenre(GameGenre.Platformer)
            _rpgGames.value = repository.getGamesByGenre(GameGenre.RPG)
        }
    }
}