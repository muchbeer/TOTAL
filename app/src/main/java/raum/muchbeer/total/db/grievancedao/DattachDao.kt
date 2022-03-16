package raum.muchbeer.total.db.grievancedao

import DattachmentModel
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DattachDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDSingleAttachment(data: DattachmentModel) : Long

    @Query("SELECT * FROM d_attachment")
    fun retrievDAttachment() : Flow<List<DattachmentModel>>

    @Query("SELECT * FROM d_attachment WHERE isUploaded = :checkStatus")
    fun retrievDListOfAttachmentBySelect(checkStatus : Boolean) : Flow<List<DattachmentModel>>

    @Query("SELECT * FROM d_attachment WHERE valuation_number = :valuation_no")
    fun retrievDListOfAttachmentByValuationNo(valuation_no : String) : Flow<List<DattachmentModel>>

    @Update
    suspend fun updateDAttachment(attachment: DattachmentModel)

    @Query("UPDATE d_attachment SET file_url=:imageUrl WHERE file_name = :fileName")
    suspend fun updateDImageAttachment(imageUrl: String, fileName: String)

}