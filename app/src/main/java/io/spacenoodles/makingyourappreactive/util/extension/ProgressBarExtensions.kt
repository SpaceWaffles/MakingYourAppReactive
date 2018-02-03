package io.spacenoodles.makingyourappreactive.util.extension

import android.graphics.PorterDuff
import android.widget.ProgressBar

fun ProgressBar?.setColor(color: Int) {
    if (this == null) return
    this.indeterminateDrawable?.let {
        it.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
    this.progressDrawable?.let {
        it.setColorFilter(color, PorterDuff.Mode.SRC_IN)
    }
}