package raum.muchbeer.total.model.hse

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import raum.muchbeer.total.db.Converters
import raum.muchbeer.total.model.hse.Hsedata

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
    @TypeConverters(Converters::class)
    val hsedata: List<Hsedata>
)


