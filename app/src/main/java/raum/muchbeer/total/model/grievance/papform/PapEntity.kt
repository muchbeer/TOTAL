package raum.muchbeer.total.model.grievance.papform

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

data class PapEntity(
    val api_key : String,
    val field_id : String
)

@Parcelize
@Entity(tableName = "paplistModel")
data class PapEntryListModel (
    @PrimaryKey(autoGenerate = false)
    val valuation_number : String,
    val full_name : String,
    val phone_number : String,
    val district : String,
    val region : String
) : Parcelable

@Entity(tableName = "pap_entry_tbl")
data class PapEntryModel(

    @PrimaryKey(autoGenerate = true) val id : Int,
    val status : String,
    val statusDesc : String,
    val paplist : List<PapEntryListModel>
)

