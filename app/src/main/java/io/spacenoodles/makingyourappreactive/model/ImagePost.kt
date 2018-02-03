package io.spacenoodles.makingyourappreactive.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImagePost (
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
    @SerializedName("cover")
    @Expose
    val cover: String? = null,
    @SerializedName("cover_width")
    @Expose
    val coverWidth: Long? = null,
    @SerializedName("cover_height")
    @Expose
    val coverHeight: Long? = null,
    @SerializedName("account_url")
    @Expose
    val accountUrl: String? = null,
    @SerializedName("account_id")
    @Expose
    val accountId: Long? = null,
    @SerializedName("privacy")
    @Expose
    val privacy: String? = null,
    @SerializedName("layout")
    @Expose
    val layout: String? = null,
    @SerializedName("views")
    @Expose
    val views: Long? = null,
    @SerializedName("link")
    @Expose
    val link: String? = null,
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
    val score: Long? = null,
    @SerializedName("is_album")
    @Expose
    val isAlbum: Boolean? = null,
    @SerializedName("favorite")
    @Expose
    val favorite: Boolean? = null,
    @SerializedName("nsfw")
    @Expose
    val nsfw: Boolean? = null,
    @SerializedName("section")
    @Expose
    val section: String? = null,
    @SerializedName("comment_count")
    @Expose
    val commentCount: Long? = null,
    @SerializedName("favorite_count")
    @Expose
    val favoriteCount: Long? = null,
    @SerializedName("topic")
    @Expose
    val topic: String? = null,
    @SerializedName("topic_id")
    @Expose
    val topicId: Long? = null,
    @SerializedName("images_count")
    @Expose
    val imagesCount: Long? = null,
    @SerializedName("in_gallery")
    @Expose
    val inGallery: Boolean? = null,
    @SerializedName("is_ad")
    @Expose
    val isAd: Boolean? = null,
    @SerializedName("tags")
    @Expose
    val tags: List<Tag>? = null,
    @SerializedName("ad_type")
    @Expose
    val adType: Long? = null,
    @SerializedName("ad_url")
    @Expose
    val adUrl: String? = null,
    @SerializedName("in_most_viral")
    @Expose
    val inMostViral: Boolean? = null,
    @SerializedName("images")
    @Expose
    val images: List<Image>? = null
)