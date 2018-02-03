package io.spacenoodles.makingyourappreactive.sync

import io.reactivex.Maybe
import io.spacenoodles.makingyourappreactive.model.ImgurResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetService {

    @GET("gallery/search/time/{page}")
    fun requestImagePosts(@Path("page") page: Int, @Query("q") searchQuery: String)
    : Maybe<ImgurResponse>
}
