package raum.muchbeer.total.db.engagedao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.hse.Hsedata

@Dao
interface EngagementDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEngagees(userData : List<EngageModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleEngage(data: EngageModel) : Long

    @Query("SELECT * FROM engagement_tbl")
    fun retrieveEngage() : LiveData<List<EngageModel>>

    @Query("SELECT * FROM engagement_tbl")
    fun retrievEngageList() : List<EngageModel>

}