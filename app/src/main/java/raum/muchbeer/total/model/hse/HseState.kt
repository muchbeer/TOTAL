package raum.muchbeer.total.model.hse

sealed class HseState<out R> {
    data class Success<T>(val data: T) : HseState<T>()
    data class Error(val error: String) : HseState<Nothing>()
}