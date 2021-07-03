package raum.muchbeer.total.model.vehicle

import androidx.room.Entity
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import raum.muchbeer.total.db.Converters

data class VehicleModel(
    @SerializedName("api_key")
    val api_key: String,
    @SerializedName("field_id")
    val field_id: String,
    @SerializedName("user_name")
    val user_name: String,
    @TypeConverters(Converters::class)
    @SerializedName("vehiclesreport")
    val vehiclesreport: List<VehiclesData>
)