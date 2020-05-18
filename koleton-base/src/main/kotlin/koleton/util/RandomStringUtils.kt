package koleton.util

import kotlin.experimental.and
import kotlin.random.Random

object RandomStringUtils {
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun random(count: Int): String {
        val bytes = ByteArray(count)
        Random.nextBytes(bytes)
        return (bytes.indices).map { i ->
                charPool[(bytes[i] and BYTE_ZERO.toByte() and (charPool.size - 1).toByte()).toInt()]
            }.joinToString(EMPTY_STRING)
    }
}