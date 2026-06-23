package ir.mrghost.gamebase.data.remote.reviews

import com.google.gson.annotations.SerializedName


data class ReviewResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("date") val date: String,
    @SerializedName("link") var link: String,
    @SerializedName("title") val title: Title,
    @SerializedName("content") val content: Content,
    @SerializedName("slug") val slug: String,
    @SerializedName("featured_media") val featuredMedia: Int? = null,
    var imageUrl: String? = null
)

data class Title(
    @SerializedName("rendered") val rendered: String
)

data class Content(
    @SerializedName("rendered") val rendered: String,
    @SerializedName("protected") val isProtected: Boolean = false
)