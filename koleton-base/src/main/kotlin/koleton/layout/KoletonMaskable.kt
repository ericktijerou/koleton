package koleton.layout

internal interface KoletonMaskable {
    fun invalidate() = Unit
    fun start() = Unit
    fun stop() = Unit
}