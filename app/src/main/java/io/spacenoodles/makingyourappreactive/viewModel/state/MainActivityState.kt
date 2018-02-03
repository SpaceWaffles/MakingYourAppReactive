package io.spacenoodles.makingyourappreactive.viewModel.state


data class MainActivityState private constructor(val status: Status,
                                   val error: Throwable? = null) {
    companion object {

        fun loading(): MainActivityState {
            return MainActivityState(Status.LOADING)
        }

        fun success(): MainActivityState {
            return MainActivityState(Status.SUCCESS)
        }

        fun complete(): MainActivityState {
            return MainActivityState(Status.COMPLETE)
        }

        fun tooShort(): MainActivityState {
            return MainActivityState(Status.TOO_SHORT)
        }

        fun error(error: Throwable): MainActivityState {
            return MainActivityState(Status.ERROR, error)
        }
    }
}