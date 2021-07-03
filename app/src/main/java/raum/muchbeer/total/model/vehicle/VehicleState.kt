package raum.muchbeer.total.model.vehicle

sealed class VehicleState<out R> {
    data class Success<T>(val data: T) : VehicleState<T>()
    data class Error(val error: String) : VehicleState<Nothing>()
    object Loading : VehicleState<Nothing>()
}