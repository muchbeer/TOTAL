package raum.muchbeer.total.model.hse

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import raum.muchbeer.total.db.Converters
import raum.muchbeer.total.model.hse.Hsedata

@Entity(tableName = "hse_general_table")
data class HseModel(
    @PrimaryKey(autoGenerate = false)
    val api_key: String,
    val field_id: String,
    val user_name: String,
    @TypeConverters(Converters::class)
    val hsedata: List<Hsedata>

)


