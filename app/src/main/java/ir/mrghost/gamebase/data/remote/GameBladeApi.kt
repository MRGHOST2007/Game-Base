package ir.mrghost.gamebase.data.remote

import ir.mrghost.gamebase.data.remote.reviews.MediaResponse
import ir.mrghost.gamebase.data.remote.reviews.ReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GameBladeApi {
    @GET("wp-json/wp/v2/posts")
    suspend fun getPosts(
        @Query("categories") categoryId: Int = 57,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 10
    ): List<ReviewResponse>

    @GET("wp-json/wp/v2/media/{id}")
    suspend fun getMedia(@Path("id") id: Int): MediaResponse
}