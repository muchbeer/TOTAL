package raum.muchbeer.total.model.grievance

import BpapDetailModel
import androidx.room.*
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
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
    @SerializedName("papdetails")
    val papdetails: List<BpapDetailModel>

)

