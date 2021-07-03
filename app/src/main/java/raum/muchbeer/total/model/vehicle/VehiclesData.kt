package raum.muchbeer.total.model.vehicle

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

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