package io.spacenoodles.makingyourappreactive.view.activity

import android.app.ActivityOptions
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.spacenoodles.makingyourappreactive.R
import io.spacenoodles.makingyourappreactive.util.extension.setColor
import io.spacenoodles.makingyourappreactive.util.extension.toastLong
import io.spacenoodles.makingyourappreactive.view.adapter.listener.LazyLoadRecyclerViewListener
import io.spacenoodles.makingyourappreactive.viewModel.MainActivityViewModel
import io.spacenoodles.makingyourappreactive.viewModel.state.MainActivityState
import io.spacenoodles.makingyourappreactive.viewModel.state.Status
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {

    private lateinit var viewModel: MainActivityViewModel

    private var searchView: SearchView? = null
    private var lazyListener: LazyLoadRecyclerViewListener? = null

    companion object {
        val EXTRA_IMAGE_URL = "imageUrl"
        val EXTRA_IMAGE_TITLE = "imageTitle"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        searchView = menu.findItem(R.id.search).actionView as SearchView
        searchView?.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.search(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                viewModel.search(newText)
                return true
            }
        })
        searchView?.setQuery(viewModel.searchQuery, true)
        return true
    }

    private fun init() {
        initViewModel()
        initLayout()
        initSubscriptions()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)
    }

    private fun initLayout() {
        progress_spinner.setColor(ContextCompat.getColor(this, R.color.colorPrimary))

        val layoutManager = LinearLayoutManager(this)
        image_post_list.layoutManager = layoutManager
        image_post_list.adapter = viewModel.imagePostAdapter
        image_post_list.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                hideKeyboard()
            }
        })

        //Setup infinite scrolling
        lazyListener = object: LazyLoadRecyclerViewListener(layoutManager) {
            override fun onLoadMore(currentPage: Int) {
                viewModel.loadMore(searchView?.query.toString())
            }
        }
        image_post_list.addOnScrollListener(lazyListener)
    }

    private fun initSubscriptions() {
        viewModel.state.observe(this, Observer<MainActivityState> {
            it?.let {
                update(it)
            }
        })

        attachClickListenerToImageAdapter()
    }

    private fun update(state: MainActivityState) {
        when (state.status) {
            Status.LOADING -> {
                progress_spinner.visibility = View.VISIBLE
                search_result_header.visibility = View.GONE
                upward_arrow.visibility = View.GONE
                instructions.visibility = View.GONE
            }

            Status.TOO_SHORT -> {
                upward_arrow.visibility = View.VISIBLE
                instructions.visibility = View.VISIBLE
                search_result_header.visibility = View.GONE
            }

            Status.SUCCESS -> {
                if (image_post_list.adapter.itemCount == 0) {
                    toastLong(R.string.no_images)
                    upward_arrow.visibility = View.VISIBLE
                    instructions.visibility = View.VISIBLE
                    search_result_header.visibility = View.GONE
                } else {
                    search_result_header.visibility = View.VISIBLE
                    search_result_header.text = resources.getString(R.string.search_result_header, searchView?.query)
                }
                lazyListener?.setLoading(false)
                progress_spinner.visibility = View.GONE
            }

            Status.COMPLETE -> {
                progress_spinner.visibility = View.GONE
            }

            Status.ERROR -> {
                Log.e("MainActivity: ", state.error?.localizedMessage)
            }
        }
    }

    private fun attachClickListenerToImageAdapter() {
        addSub(viewModel.imagePostAdapter.clickStream.observeOn(AndroidSchedulers.mainThread())?.subscribe({
            searchView?.clearFocus()
            var intent = Intent(this@MainActivity, ImageDetailActivity::class.java)
            viewModel.imagePostAdapter.let { adapter ->
                intent.putExtra(MainActivity.EXTRA_IMAGE_URL, it.data.images?.first()?.link?:"")
                intent.putExtra(MainActivity.EXTRA_IMAGE_TITLE, it.data.title)
            }
            val options = ActivityOptions
                    .makeSceneTransitionAnimation(this@MainActivity, it.imageView, ViewCompat.getTransitionName(it.imageView))
            startActivity(intent, options.toBundle())
        }))
    }

    private fun hideKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // check if no view has focus:
        val v = this.currentFocus ?: return

        inputManager.hideSoftInputFromWindow(v.windowToken, 0)
    }
}
