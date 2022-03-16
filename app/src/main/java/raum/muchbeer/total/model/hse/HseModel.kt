package raum.muchbeer.total.model.hse

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

@Entity(tableName = "hse_general_table")
data class HseModel(
    @SerializedName("api_key")
    val api_key: String,
    val field_id: String,
    @SerializedName("user_name")
    val user_name: String,
    @SerializedName("hse_key")
    @PrimaryKey(autoGenerate = false)
    val hse_key : String,
    @SerializedName("hsedata")
    val hsedata: List<Hsedata>
)

@Entity(tableName = "hse")
data class Hsedata(
    val accomodations: String,
    val anyobservation: String,
    val anysecurityissue: String,
    val car_inspection_report: String,
    val didanyincidentoccor: String,
    val ifincidentoccored: String,
    @PrimaryKey(autoGenerate = false)
    val primary_key: String,
    val reg_date: String,
    val securityissueyes: String,
    val toolboxtopics: String,
    val anycomment : String)

