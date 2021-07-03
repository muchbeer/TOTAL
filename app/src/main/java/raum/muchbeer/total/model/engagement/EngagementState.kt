package raum.muchbeer.total.model.engagement

sealed class EngagementState<out R> {
    data class Success<T>(val data: T) : EngagementState<T>()
    data class Error(val error: String) : EngagementState<Nothing>()
}