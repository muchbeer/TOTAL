package raum.muchbeer.total.db.grievancedao

import androidx.lifecycle.LiveData
import androidx.room.*
import raum.muchbeer.total.model.ImageFirestore
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

    @Query("SELECT * FROM takeToFirestore")
    suspend fun retrieveListOfImages() : List<ImageFirestore>

    @Query("UPDATE takeToFirestore SET imageUrl=:imageUrl WHERE fileName = :fileName")
    fun updateImages(imageUrl: String, fileName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImages(data : ImageFirestore) : Long

    @Query("SELECT * FROM d_attachment_tbl WHERE primary_key LIKE :unique_data")
    suspend fun retrieveSingleAttachment(unique_data : String) : DattachmentModel

    @Query("SELECT * FROM d_attachment_tbl")
    suspend fun retrieveListOfAttachment() : List<DattachmentModel>

    @Query("DELETE FROM d_attachment_tbl")
    suspend fun clear()
}