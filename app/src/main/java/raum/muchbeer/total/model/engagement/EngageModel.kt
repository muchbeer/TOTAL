package raum.muchbeer.total.model.engagement

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "engagement_tbl")
data class EngageModel(
    @SerializedName("api_key")
    val api_key: String,
    @SerializedName("field_id")
    val field_id: String,
    @SerializedName("keypoints")
    val keypoints: String,
    @SerializedName("listofparticipants")
    val listofparticipants: String,
    @SerializedName("meeting_time")
    val meeting_time: String,
    @SerializedName("number_of_participants")
    val number_of_participants: String,
    @SerializedName("user_name")
    val user_name: String,
     @SerializedName("locationtype")
     val locationtype : String,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("primary_key")
    val primary_key : String
)