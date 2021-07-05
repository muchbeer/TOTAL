package raum.muchbeer.total.model.grievance

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "d_attachment_tbl")
data class DattachmentModel(
    @SerializedName("category_name")
    val category_name: String,
    @SerializedName("file_type")
    val file_type: String,
    @SerializedName("file_url")
    val file_url: String,
    @SerializedName("primary_key")
    @PrimaryKey(autoGenerate = false)
    val primary_key: String
) : Parcelable