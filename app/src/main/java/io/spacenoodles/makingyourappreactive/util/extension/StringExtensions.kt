package io.spacenoodles.makingyourappreactive.util.extension

import android.webkit.URLUtil

fun String?.isValidURL(): Boolean {
    if (this == null) return false
    return this.isNotEmpty() && URLUtil.isValidUrl(this)
}
