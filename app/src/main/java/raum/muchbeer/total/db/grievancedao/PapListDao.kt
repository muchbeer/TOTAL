package raum.muchbeer.total.db.grievancedao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel

@Dao
interface PapListDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPapList(papUsers : List<PapEntryListModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSinglePap(papUser: PapEntryListModel) : Long

    @Query("SELECT * FROM paplistModel")
    fun retrievePapUsers() : Flow<List<PapEntryListModel>>

    @Query("SELECT * FROM paplistModel where full_name like  :paps")
    fun getSearchResults(paps : String) : LiveData<List<PapEntryListModel>>

    @Query("SELECT * FROM paplistModel where full_name like '%' || :papList || '%'")
    fun searchPaps(papList : String) : Flow<List<PapEntryListModel>>

    @Query("DELETE FROM paplistModel")
    suspend fun deleteAllFoods()
}