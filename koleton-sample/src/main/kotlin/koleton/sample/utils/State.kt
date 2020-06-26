package koleton.sample.utils

import androidx.annotation.IntDef

@Retention(AnnotationRetention.RUNTIME)
@IntDef(
    State.NONE,
    State.LOADED,
    State.LOADING,
    State.INITIAL_LOADED,
    State.FAILED
)

annotation class State {
    companion object {
        const val NONE = 0
        const val LOADED = 1
        const val LOADING = 2
        const val INITIAL_LOADED = 4
        const val FAILED = 5
    }
}