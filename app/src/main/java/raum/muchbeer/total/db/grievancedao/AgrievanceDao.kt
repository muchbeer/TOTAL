package raum.muchbeer.total.db.grievancedao

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.total.model.grievance.AgrienceModel

@Dao
interface AgrievanceGeneralDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrievanceList(userData : List<AgrienceModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleGrievanceGen(data: AgrienceModel) : Long

    @Query("SELECT * FROM a_grievance_form1")
    fun retrieveAGrievanceGeneral() : Flow<List<AgrienceModel>>

    @Query("SELECT * FROM a_grievance_form1 WHERE  primary_key LIKE :primaryKey")
     fun retrieveAGrievenceWithPrimaryKey(primaryKey : String) : Flow<List<AgrienceModel>>
}