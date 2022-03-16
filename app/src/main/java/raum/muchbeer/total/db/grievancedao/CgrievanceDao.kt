package raum.muchbeer.total.db.grievancedao

import CgrievanceModel
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface CgrievanceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrievanceees(userData : List<CgrievanceModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleGrievancee(data: CgrievanceModel) : Long

    @Query("SELECT * FROM c_grievance")
    fun retrieveAGrievance() : Flow<List<CgrievanceModel>>

    @Query("SELECT * FROM c_grievance WHERE full_name =:full_name")
    fun searchAListOfGrieveWithValuationNo(full_name : String) : Flow<List<CgrievanceModel>>

    @Update
    suspend fun updateCgrievancewithAttach(griev : CgrievanceModel)

    @Query("DELETE FROM c_grievance")
    suspend fun clear()
}