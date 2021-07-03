package raum.muchbeer.total.db.grievancedao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import raum.muchbeer.total.model.grievance.CgrievanceModel

@Dao
interface CgrievanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrievanceees(userData : List<CgrievanceModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleGrievancee(data: CgrievanceModel) : Long

    @Query("SELECT * FROM c_grievance_enter_values")
    fun retrieveGrievancee() : LiveData<List<CgrievanceModel>>

    @Query("SELECT * FROM c_grievance_enter_values")
    suspend fun retrieveSingleGrieve() : CgrievanceModel

    @Query("DELETE FROM c_grievance_enter_values")
    suspend fun clear()
}