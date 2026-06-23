package ir.mrghost.gamebase.data.remote

import ir.mrghost.gamebase.data.remote.reviews.MediaResponse
import ir.mrghost.gamebase.data.remote.reviews.ReviewResponse

class GameBladeRepository(
    private val api: GameBladeApi
) {
    suspend fun getReviews(page: Int = 1, perPage: Int = 10): List<ReviewResponse> {
        return try {
            api.getPosts(categoryId = 57, page = page, perPage = perPage)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getMedia(id: Int): MediaResponse? {
        return try {
            api.getMedia(id)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}