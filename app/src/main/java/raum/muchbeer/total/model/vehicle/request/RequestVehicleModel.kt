package raum.muchbeer.total.model.vehicle.request

import com.google.gson.annotations.SerializedName


data class RequestVehicleModel(
    @SerializedName("status")
    val status: String,
    @SerializedName("statusDesc")
    val statusDesc: String,
    @SerializedName("vehicles")
    val vehicles: List<Vehicle>
)