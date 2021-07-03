package raum.muchbeer.total.model.grievance

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "c_grievance_enter_values")
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
    val full_name : String
) : Parcelable