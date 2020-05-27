package koleton.lifecycle

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver

internal object GlobalLifecycle : Lifecycle() {

    override fun addObserver(observer: LifecycleObserver) {}

    override fun removeObserver(observer: LifecycleObserver) {}

    override fun getCurrentState() = State.RESUMED
}
