package ir.mrghost.gamebase.data.remote.reviews

import com.google.gson.annotations.SerializedName

data class MediaResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("source_url") val sourceUrl: String,
    @SerializedName("media_details") val mediaDetails: MediaDetails?
)

data class MediaDetails(
    @SerializedName("width") val width: Int,
    @SerializedName("height") val height: Int
)