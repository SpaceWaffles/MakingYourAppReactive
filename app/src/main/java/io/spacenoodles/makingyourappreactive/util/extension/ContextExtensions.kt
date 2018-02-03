package io.spacenoodles.makingyourappreactive.util.extension

import android.content.Context
import android.widget.Toast

fun Context.toastLong(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_LONG).show()
}

fun Context.toastLong(textRes: Int) = Toast
        .makeText(this, textRes, Toast.LENGTH_LONG)
        .show()