package koleton.util

private const val RANDOM_ALPHANUMERIC_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXTZabcdefghiklmnopqrstuvwxyz0123456789"

internal fun getRandomAlphaNumericString(length: Int): String {
    return (1..length)
            .map { RANDOM_ALPHANUMERIC_CHARS.random() }
            .joinToString(EMPTY_STRING)
}
