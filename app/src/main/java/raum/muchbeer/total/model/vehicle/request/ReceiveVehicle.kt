package raum.muchbeer.total.model.vehicle.request

import com.google.gson.annotations.SerializedName

data class ReceiveVehicle(
    @SerializedName("api_key")
    val api_key: String,
    @SerializedName("field_id")
    val field_id: String
)