package ir.mrghost.gamebase.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ir.mrghost.gamebase.AppModule
import ir.mrghost.gamebase.data.remote.reviews.ReviewResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewsViewModel : ViewModel() {

    private val repository = AppModule.provideGameBladeRepository()

    private val _reviews = MutableStateFlow<List<ReviewResponse>>(emptyList())
    val reviews: StateFlow<List<ReviewResponse>> = _reviews.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _isLoadingMore = MutableStateFlow(false)
    val isLoadingMore: StateFlow<Boolean> = _isLoadingMore.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentPage = 1
    private var hasMorePages = true
    private val perPage = 10

    fun loadReviews() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            currentPage = 1
            hasMorePages = true

            val result = repository.getReviews(page = currentPage, perPage = perPage)
            if (result.isEmpty()) {
                _error.value = "No reviews found"
                _isLoading.value = false
                return@launch
            } else {
                for (review in result) {
                    review.featuredMedia?.let { mediaId ->
                        val media = repository.getMedia(mediaId)
                        review.imageUrl = media?.sourceUrl
                    }
                }
                _reviews.value = result
            }

            _isLoading.value = false
        }
    }

    fun loadMore(){
        if (isLoadingMore.value || !hasMorePages) return

        viewModelScope.launch {
            _isLoadingMore.value = true
            val nextPage = currentPage+1
            val result = repository.getReviews(nextPage, perPage)

            if (result.isNotEmpty()){
                val currentList = reviews.value.toMutableList()
                result.forEach {
                    it.featuredMedia?.let { mediaId ->
                        val media = repository.getMedia(mediaId)
                        it.imageUrl = media?.sourceUrl
                    }
                }
                currentList.addAll(result)
                _reviews.value = currentList
                checkHasMore(result)
            }
        }
    }

    private fun checkHasMore(reviews: List<ReviewResponse>) {
        // If we got less than perPage, there are no more pages
        hasMorePages = reviews.size >= perPage
    }

}