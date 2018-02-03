package io.spacenoodles.makingyourappreactive.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImgurResponse(
        @SerializedName("data")
        @Expose
        val data: List<ImagePost>? = null
)