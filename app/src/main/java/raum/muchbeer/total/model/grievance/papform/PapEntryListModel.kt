package raum.muchbeer.total.model.grievance.papform

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

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