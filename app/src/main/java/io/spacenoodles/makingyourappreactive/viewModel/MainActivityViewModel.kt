package io.spacenoodles.makingyourappreactive.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.spacenoodles.makingyourappreactive.App
import io.spacenoodles.makingyourappreactive.model.ImagePost
import io.spacenoodles.makingyourappreactive.model.ImgurResponse
import io.spacenoodles.makingyourappreactive.sync.repository.ImagePostRepository
import io.spacenoodles.makingyourappreactive.util.extension.isValidURL
import io.spacenoodles.makingyourappreactive.view.adapter.ImagePostAdapter
import io.spacenoodles.makingyourappreactive.viewModel.state.MainActivityState
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivityViewModel : ViewModel() {

    val state: MutableLiveData<MainActivityState>

    init {
        App.component.inject(this)
        state = MutableLiveData()
        initSearch()
    }

    @Inject lateinit var imageRepo: ImagePostRepository

    private var currentPage = 0
    var imagePostAdapter = ImagePostAdapter(ArrayList())
    var searchQuery = ""

    private lateinit var searchEmitter: PublishSubject<String>

    fun search(query: String) {
        if (searchQuery != query) {
            imagePostAdapter.items = ArrayList()
            imagePostAdapter.notifyDataSetChanged()
        }
        searchQuery = query
        searchEmitter.onNext(query)
    }

    private fun searchForImages(query: String): Maybe<ImgurResponse> {
        return imageRepo.getImagePosts(currentPage, query)
    }

    fun loadMore(searchQuery: String) {
        currentPage++
        searchEmitter.onNext(searchQuery)
    }

    private fun initSearch() {
        searchEmitter = PublishSubject.create<String>()
        searchEmitter
                .subscribeOn(Schedulers.io())
                .debounce(250, TimeUnit.MILLISECONDS)
                .doOnNext {
                    if (it.length < 2) {
                        state.postValue(MainActivityState.tooShort())
                    }
                }
                .filter { it.length > 1 }
                .doOnNext { state.postValue(MainActivityState.loading()) }
                .doOnTerminate { state.postValue(MainActivityState.complete()) }
                .switchMap {
                    searchForImages(it).toObservable()
                }
                .map {
                    val newData = ImgurResponse(it.data?.filter { item ->
                        item.images?.isNotEmpty() == true && item.images.first().link.isValidURL()
                        && item.nsfw == false
                        && item.images.first().size ?:Long.MAX_VALUE <= 1500000L
                    })
                    newData
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { response ->
                    updateAdapter(response.data)
                    state.postValue(MainActivityState.success())
                }
                .subscribe({},
                        {
                            state.postValue(MainActivityState.error(it))
                        })
    }

    private fun updateAdapter(data: List<ImagePost>?) {
        //If the search is at least length 2, put the items in the adapter, otherwise clear the adapter
        if (searchQuery.length > 1) {
            imagePostAdapter.items = imagePostAdapter.items.plus(data ?: ArrayList())
            imagePostAdapter.notifyDataSetChanged()
        } else {
            imagePostAdapter.items = ArrayList()
        }
    }
}