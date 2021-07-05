package raum.muchbeer.total.model.vehicle

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import raum.muchbeer.total.db.Converters

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
    @TypeConverters(Converters::class)
    @SerializedName("vehiclesreport")
    val vehiclesreport: List<VehiclesData>
)