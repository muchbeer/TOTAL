package raum.muchbeer.total.model.grievance

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import raum.muchbeer.total.db.Converters

@Parcelize
@Entity(tableName = "b_grievance_identification")
data class BpapDetailModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("valuation_number")
    val valuation_number: String,
    @TypeConverters(Converters::class)
    @SerializedName("grievance")
    val grievance: List<CgrievanceModel>,
    @TypeConverters(Converters::class)
    @SerializedName("attachments")
    val attachments: List<DattachmentModel>
) : Parcelable