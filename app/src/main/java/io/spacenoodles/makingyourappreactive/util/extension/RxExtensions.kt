package io.spacenoodles.makingyourappreactive.util.extension

import io.reactivex.Maybe

fun <T> maybe(value: T?): Maybe<T> {
    return if(value == null) Maybe.empty()
    else Maybe.just(value)
}