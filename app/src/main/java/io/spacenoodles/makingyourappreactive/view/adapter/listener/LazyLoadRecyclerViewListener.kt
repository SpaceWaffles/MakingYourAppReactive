package io.spacenoodles.makingyourappreactive.view.adapter.listener

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

abstract class LazyLoadRecyclerViewListener(private val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    private var loading = true //True if we are still waiting for data to load
    private var currentPage = 1

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItemCount = recyclerView.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

        if (!loading && totalItemCount - visibleItemCount <= firstVisibleItem + VISIBLE_THRESHOLD) {
            //End of list has been reached
            if (hasMore(totalItemCount)) {
                loading = true
                onLoadMore(++currentPage)
            }
        }
    }

    fun hasMore(totalItemCount: Int): Boolean {
        return true
    }

    fun reset() {
        loading = true
        currentPage = 1
    }

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    abstract fun onLoadMore(currentPage: Int)

    companion object {
        private val VISIBLE_THRESHOLD = 5 //Number of items left in list before we start loading more
    }
}