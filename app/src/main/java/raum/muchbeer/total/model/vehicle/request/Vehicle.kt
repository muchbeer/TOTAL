package raum.muchbeer.total.model.vehicle.request

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "request_vehicle_tbl")
data class Vehicle(
    @SerializedName("driver_phone_number")
    val driver_phone_number: String,
    @SerializedName("drivers_name")
    val drivers_name: String,
    @PrimaryKey
    @SerializedName("vehicle_number")
    val vehicle_number: String
) : Parcelable