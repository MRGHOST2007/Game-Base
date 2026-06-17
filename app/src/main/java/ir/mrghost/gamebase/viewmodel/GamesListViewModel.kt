package ir.mrghost.gamebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.mrghost.gamebase.AppModule
import ir.mrghost.gamebase.data.local.games.Game
import ir.mrghost.gamebase.data.local.GameRepository
import ir.mrghost.gamebase.utils.GameGenre
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn

class GamesListViewModel(
    private val repository: GameRepository = AppModule.provideGameRepository()
) : ViewModel() {
    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    @OptIn(FlowPreview::class)
    val debounced = _query
        .debounce(500)
        .stateIn(viewModelScope, SharingStarted.Eagerly, "")
    private val _selectedGenres = MutableStateFlow(emptyList<GameGenre>())
    val selectedGenres: StateFlow<List<GameGenre>> = _selectedGenres.asStateFlow()

    val filteredGames: StateFlow<List<Game>> = combine(debounced, selectedGenres) { query, genres ->
        val allGames = repository.getAllGames()
        val searched = if (query.isBlank()) allGames
        else allGames.filter { it.title.contains(query, ignoreCase = true) }
        if (genres.isEmpty()) searched
        else searched.filter { it.genre in genres }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun updateGenres(genres: List<GameGenre>) {
        _selectedGenres.value = genres
    }
}