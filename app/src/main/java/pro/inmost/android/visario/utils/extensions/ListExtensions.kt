package pro.inmost.android.visario.utils.extensions

fun <T>MutableList<T>.replaceAll(collection: Collection<T>): MutableList<T> {
    clear()
    addAll(collection)
    return this
}