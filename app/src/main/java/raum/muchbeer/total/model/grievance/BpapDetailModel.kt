import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import raum.muchbeer.total.db.Converters

@Parcelize
@Entity(tableName = "b_grievance")
data class BpapDetailModel(
    @PrimaryKey(autoGenerate = false)
    @SerializedName("valuation_number")
    val valuation_number: String,
    @SerializedName("a_grievance_id")
    val a_grievance_id : String,
    @SerializedName("username")
    val user_name : String,
    @TypeConverters(Converters::class)
    @SerializedName("grievance")
    val grievance: List<CgrievanceModel>,
    @TypeConverters(Converters::class)
    @SerializedName("attachments")
    val attachments: List<DattachmentModel>
) : Parcelable

@Parcelize
@Entity(tableName = "c_grievance")
data class CgrievanceModel(
    @SerializedName("agreetosign")
    val agreetosign: String,
    @SerializedName("notagreetosign")
    val notagreetosign: String,
    @SerializedName("satisfiedwithcontract")
    val satisfiedwithcontract: String,
    @SerializedName("notsatisfiedwithcontract")
    val notsatisfiedwithcontract: String,
    @SerializedName("anyrecomendations")
    val anyrecomendations: String,
    @SerializedName("recomendations")
    val recomendations: String,
    @SerializedName("gstatus")
    val gstatus: String,
    @SerializedName("reg_date")
    val reg_date: String,
    @SerializedName("grievancetype")
    val grievancetype : String,
    @SerializedName("grievanceexplanation")
    val grievanceexplanation : String,
    @SerializedName("full_name")
    @PrimaryKey(autoGenerate = false)
    val full_name : String,
    @SerializedName("inquirytype")
    val inquirytype : String,
    @SerializedName("gender")
    val gender : String,
    @SerializedName("valuation_number")
    val valuation_no : String
) : Parcelable

@Parcelize
@Entity(tableName = "d_attachment")
data class DattachmentModel(
    @SerializedName("category_name")
    val category_name: String,
    @PrimaryKey(autoGenerate = false)
    @SerializedName("file_type")
    val file_name: String,
    @SerializedName("file_url")
    val file_url: String,
    @SerializedName("isUploaded")
    val isUploaded : Boolean,
    @SerializedName("valuation_number")
    val valuation_number: String
) : Parcelable
