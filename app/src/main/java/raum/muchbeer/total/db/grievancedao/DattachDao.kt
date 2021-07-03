package raum.muchbeer.total.db.grievancedao

import androidx.lifecycle.LiveData
import androidx.room.*
import raum.muchbeer.total.model.grievance.DattachmentModel

@Dao
interface DattachDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAttachmentList(userData : List<DattachmentModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleAttachment(data: DattachmentModel) : Long

    @Query("SELECT * FROM d_attachment_tbl")
    fun retrieveAttachment() : LiveData<List<DattachmentModel>>

    @Update
    fun update(attachment: DattachmentModel)

    @Query("SELECT * FROM d_attachment_tbl")
    suspend fun retrieveListOfAttachment() : List<DattachmentModel>

    @Query("SELECT * FROM d_attachment_tbl")
    suspend fun retrieveSingleAttachment() : DattachmentModel

    @Query("DELETE FROM d_attachment_tbl")
    suspend fun clear()
}