package raum.muchbeer.total.model.hse

import androidx.room.Entity
import androidx.room.PrimaryKey

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

