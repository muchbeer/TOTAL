package raum.muchbeer.total.db.grievancedao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import raum.muchbeer.total.model.grievance.BpapDetailModel

@Dao
interface BpapDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBpapDetailIDList(userData : List<BpapDetailModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSinglePapDetailD(data: BpapDetailModel) : Long

    @Query("SELECT * FROM b_grievance_identification")
    fun retrieveBpapDetailD() : LiveData<List<BpapDetailModel>>
}