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

    private val _isLoaded = MutableStateFlow(false)
    val isLoaded: StateFlow<Boolean> = _isLoaded.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private var currentPage = 1
    private var hasMorePages = true
    private val perPage = 10

    fun loadReviews(forceRefresh: Boolean = false) {

        if (_isLoaded.value && !forceRefresh) {
            return
        }

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

            _isLoaded.value = true
            _isLoading.value = false
        }
    }

    fun loadMore() {
        if (isLoadingMore.value || !hasMorePages) return

        viewModelScope.launch {
            _isLoadingMore.value = true
            val nextPage = currentPage + 1
            val result = repository.getReviews(nextPage, perPage)

            if (result.isNotEmpty()) {
                for (it in result) {
                    it.featuredMedia?.let { mediaId ->
                        val media = repository.getMedia(mediaId)
                        it.imageUrl = media?.sourceUrl
                    }
                    if (it.link.isEmpty()) {
                        it.link = "https://gameblade.ir/review-${it.slug}/"
                    }
                }
                val currentList = _reviews.value.toMutableList()
                currentList.addAll(result)
                _reviews.value = currentList

                currentPage = nextPage
            }

            _isLoadingMore.value = false
        }
    }

}