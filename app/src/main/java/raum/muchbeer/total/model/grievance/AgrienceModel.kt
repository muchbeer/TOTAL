package raum.muchbeer.total.model.grievance

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import raum.muchbeer.total.db.Converters


@Entity(tableName = "a_grievance_form1")
data class AgrienceModel(
    @PrimaryKey(autoGenerate = false)
    val primary_key : String,
    @SerializedName("api_key")
    val apiKey: String,
    @SerializedName("field_id")
    val field_id: String,
    @SerializedName("user_name")
    val user_name: String,
    @TypeConverters(Converters::class)
    @SerializedName("papdetails")
    val papdetails: List<BpapDetailModel>

)
