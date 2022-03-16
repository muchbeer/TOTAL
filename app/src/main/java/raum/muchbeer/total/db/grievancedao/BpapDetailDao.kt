package raum.muchbeer.total.db.grievancedao

import BpapDetailModel
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BpapDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBpapDetailIDList(userData : List<BpapDetailModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSinglePapDetailD(data: BpapDetailModel) : Long

    @Query("SELECT * FROM b_grievance")
    fun retrieveBpapDetailD() : Flow<List<BpapDetailModel>>

    @Query("SELECT * FROM b_grievance WHERE a_grievance_id = :primaryKey")
    fun retrieveBpapDetailDFromPrimaryKey(primaryKey: String) : Flow<List<BpapDetailModel>>

    @Query("SELECT * FROM b_grievance WHERE user_name = :username")
    fun retrievePapsEnteredWithUserName(username : String) : Flow<List<BpapDetailModel>>
}