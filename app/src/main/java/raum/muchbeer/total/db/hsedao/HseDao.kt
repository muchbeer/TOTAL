package raum.muchbeer.total.db.hsedao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.hse.Hsedata

@Dao
interface HseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHsees(userData : List<Hsedata>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleHse(data: Hsedata) : Long

    @Query("SELECT * FROM hse")
    fun retrieveHse() : LiveData<List<Hsedata>>

    @Query("SELECT * FROM hse")
    fun retrieveList() : List<Hsedata>
}