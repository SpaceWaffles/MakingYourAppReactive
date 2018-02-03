package io.spacenoodles.makingyourappreactive.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Tag (
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("display_name")
    @Expose
    val displayName: String? = null,
    @SerializedName("followers")
    @Expose
    val followers: Long? = null,
    @SerializedName("total_items")
    @Expose
    val totalItems: Long? = null,
    @SerializedName("following")
    @Expose
    val following: Boolean? = null,
    @SerializedName("background_hash")
    @Expose
    val backgroundHash: String? = null,
    @SerializedName("accent")
    @Expose
    val accent: String? = null,
    @SerializedName("background_is_animated")
    @Expose
    val backgroundIsAnimated: Boolean? = null,
    @SerializedName("thumbnail_is_animated")
    @Expose
    val thumbnailIsAnimated: Boolean? = null,
    @SerializedName("is_promoted")
    @Expose
    val isPromoted: Boolean? = null,
    @SerializedName("description")
    @Expose
    val description: String? = null
)
