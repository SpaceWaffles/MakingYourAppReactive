package io.spacenoodles.makingyourappreactive.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("id")
    @Expose
    val id: String? = null,
    @SerializedName("title")
    @Expose
    val title: String? = null,
    @SerializedName("description")
    @Expose
    val description: String? = null,
    @SerializedName("datetime")
    @Expose
    val datetime: Long? = null,
    @SerializedName("type")
    @Expose
    val type: String? = null,
    @SerializedName("animated")
    @Expose
    val animated: Boolean? = null,
    @SerializedName("width")
    @Expose
    val width: Long? = null,
    @SerializedName("height")
    @Expose
    val height: Long? = null,
    @SerializedName("size")
    @Expose
    val size: Long? = null,
    @SerializedName("views")
    @Expose
    val views: Long? = null,
    @SerializedName("bandwidth")
    @Expose
    val bandwidth: Long? = null,
    @SerializedName("favorite")
    @Expose
    val favorite: Boolean? = null,
    @SerializedName("nsfw")
    @Expose
    val nsfw: Boolean? = null,
    @SerializedName("is_ad")
    @Expose
    val isAd: Boolean? = null,
    @SerializedName("in_most_viral")
    @Expose
    val inMostViral: Boolean? = null,
    @SerializedName("has_sound")
    @Expose
    val hasSound: Boolean? = null,
    @SerializedName("tags")
    @Expose
    val tags: List<Tag>? = null,
    @SerializedName("ad_type")
    @Expose
    val adType: Long? = null,
    @SerializedName("ad_url")
    @Expose
    val adUrl: String? = null,
    @SerializedName("in_gallery")
    @Expose
    val inGallery: Boolean? = null,
    @SerializedName("link")
    @Expose
    val link: String? = null,
    @SerializedName("favorite_count")
    @Expose
    val favoriteCount: Long? = null,
    @SerializedName("ups")
    @Expose
    val ups: Long? = null,
    @SerializedName("downs")
    @Expose
    val downs: Long? = null,
    @SerializedName("poLongs")
    @Expose
    val poLongs: Long? = null,
    @SerializedName("score")
    @Expose
    val score: Long? = null
)