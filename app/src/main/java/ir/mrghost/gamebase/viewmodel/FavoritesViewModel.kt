package ir.mrghost.gamebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.mrghost.gamebase.AppModule
import ir.mrghost.gamebase.data.local.favorites.FavoriteEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class FavoritesViewModel : ViewModel() {

    private val favoritesRepository = AppModule.provideFavoritesRepository()

    val favorites: StateFlow<List<FavoriteEntity>> = favoritesRepository.getAllFavorites()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(3000),
            initialValue = emptyList()
        )

}