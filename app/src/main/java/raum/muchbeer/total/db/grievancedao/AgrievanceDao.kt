package raum.muchbeer.total.db.grievancedao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import raum.muchbeer.total.model.grievance.AgrienceModel

@Dao
interface AgrievanceGeneralDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGrievanceList(userData : List<AgrienceModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleGrievanceGen(data: AgrienceModel) : Long

    @Query("SELECT * FROM a_grievance_form1")
    fun retrieveGrievanceGeneral() : LiveData<List<AgrienceModel>>

    @Query("SELECT * FROM a_grievance_form1")
   suspend fun retrieveListGrievance() : List<AgrienceModel>

 //   @Query("SELECT * FROM user WHERE first_name LIKE :search "
    @Query("SELECT * FROM a_grievance_form1 WHERE  user_name LIKE :fullName")
    suspend fun getSearchedGrievByName(fullName : String) : AgrienceModel
}