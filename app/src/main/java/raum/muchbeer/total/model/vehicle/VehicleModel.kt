package raum.muchbeer.total.model.vehicle

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


@Entity(tableName = "vehicle_model_tbl")
data class VehicleModel(
    @SerializedName("api_key")
    val api_key: String,
    @SerializedName("field_id")
    val field_id: String,
    @SerializedName("user_name")
    val user_name: String,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("vehicle_key")
    val vehicle_key : String,
    @SerializedName("vehiclesreport")
    val vehiclesreport: List<VehiclesData>
)

@Entity(tableName = "vehicle_tbl")
data class VehiclesData(
    @SerializedName("distance_covered")
    val distance_covered: String,
    @SerializedName("hours_covered")
    val hours_covered: String,
    @PrimaryKey
    @SerializedName("primary_key")
    val primary_key: String,
    @SerializedName("reg_date")
    val reg_date: String,
    @SerializedName("vehicle_number")
    val vehicle_number: String
)