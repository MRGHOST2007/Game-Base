package ir.mrghost.gamebase.viewmodel

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.mrghost.gamebase.AppModule
import ir.mrghost.gamebase.data.remote.reviews.ReviewResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewDetailViewModel: ViewModel() {

    val repository = AppModule.provideGameBladeRepository()

    private val _review = MutableStateFlow<ReviewResponse?>(null)
    val review: StateFlow<ReviewResponse?> = _review.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadReview(reviewUrl : String){
        viewModelScope.launch {
            _isLoading.value = true
            val result = repository.getReviews()
            _review.value = result.find { it.link == reviewUrl}
            _isLoading.value = false

        }
    }
}