package raum.muchbeer.total.model.grievance.papform

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity(tableName = "pap_entry_tbl")
data class PapEntryModel(

    @PrimaryKey(autoGenerate = true) val id : Int,
    val status : String,
     val statusDesc : String,
     val paplist : List<PapEntryListModel>
)