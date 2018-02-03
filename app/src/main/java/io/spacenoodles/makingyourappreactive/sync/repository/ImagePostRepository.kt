package io.spacenoodles.makingyourappreactive.sync.repository

import io.reactivex.Maybe
import io.reactivex.schedulers.Schedulers
import io.spacenoodles.makingyourappreactive.model.ImgurResponse
import io.spacenoodles.makingyourappreactive.sync.NetService
import io.spacenoodles.makingyourappreactive.util.extension.maybe

class ImagePostRepository(private val netService: NetService) {

    private var cache = ImagePostCache()

    fun getImagePosts(page: Int, searchQuery: String): Maybe<ImgurResponse> {

        return Maybe
                .concat(cache.getPageOfImagePosts(page, searchQuery).subscribeOn(Schedulers.io()),
                        netService.requestImagePosts(page, searchQuery)
                                .doOnSuccess {
                                    cache.update(page, searchQuery, it)
                                }.subscribeOn(Schedulers.io()))
                .firstElement()
    }

    class ImagePostCache {
        private var cachedImagePosts = HashMap<Int, ImgurResponse>()
        var searchQuery = ""

        private fun clear() {
            cachedImagePosts.clear()
        }

        fun getPageOfImagePosts(page: Int, searchQuery: String): Maybe<ImgurResponse> {
            if (this.searchQuery != searchQuery) {
                clear()
            }
            this.searchQuery = searchQuery
            if (cachedImagePosts.keys.contains(page)) {
                return maybe(cachedImagePosts[page])
            }
            return Maybe.empty()
        }

        fun update(page: Int, query: String, data: ImgurResponse) {
            if (this.searchQuery == query)
                cachedImagePosts[page] = data
        }
    }
}