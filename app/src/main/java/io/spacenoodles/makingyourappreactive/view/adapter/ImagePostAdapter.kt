package io.spacenoodles.makingyourappreactive.view.adapter

import android.app.Service
import android.content.ClipData
import android.content.ClipboardManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import io.spacenoodles.makingyourappreactive.R
import io.spacenoodles.makingyourappreactive.model.ImagePost
import io.spacenoodles.makingyourappreactive.util.extension.inflate
import io.spacenoodles.makingyourappreactive.util.extension.toastLong
import kotlinx.android.synthetic.main.list_item_image_post.view.*

class ImagePostAdapter(items: List<ImagePost>) : RecyclerView.Adapter<ImagePostAdapter.ViewHolder>() {
    var items = items

    private val onClickSubject = PublishSubject.create<ImageItem>()
    val clickStream: Observable<ImageItem> get() = onClickSubject.hide()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.list_item_image_post))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], { onClickSubject.onNext(it) })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ImagePost, click: (ImageItem) -> Unit = {}) {
            itemView.setOnClickListener(null)
            Picasso
                    .with(itemView.context)
                    .cancelRequest(itemView.image_view)
            itemView.image_view.setImageResource(R.drawable.ic_image_24dp)
            Picasso
                    .with(itemView.context)
                    .load(item.images?.first()?.link)
                    .placeholder(R.drawable.ic_image_24dp)
                    .fit()
                    .centerCrop()
                    .into(itemView.image_view)

            itemView.image_view.setOnClickListener { click.invoke(ImageItem(itemView.image_view, item)) }
            itemView.link_button.setOnClickListener {
                val clipboard = itemView.context.getSystemService(Service.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("Link", item.images?.first()?.link)
                clipboard.primaryClip = clip
                itemView.context.toastLong(itemView.context.getString(R.string.link_copied))
            }

            itemView.score_text.text = itemView.context.getString(R.string.score, item.score)
            itemView.title_text.text = item.title
        }
    }

    data class ImageItem(val imageView: View, val data: ImagePost)
}