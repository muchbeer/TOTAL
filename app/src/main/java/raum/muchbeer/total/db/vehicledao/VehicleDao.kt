package raum.muchbeer.total.db.vehicledao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.model.vehicle.VehicleModel
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.Vehicle

@Dao
interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicles(userData : List<VehiclesData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleVehicle(data: VehiclesData) : Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleVehicleData(data: Vehicle) : Long

    @Query("SELECT * FROM vehicle_tbl")
    fun retrieveVehicle() : LiveData<List<VehiclesData>>

    @Query("SELECT * FROM vehicle_tbl")
     suspend fun retrieveList() : List<VehiclesData>

    @Query("SELECT * FROM vehicle_tbl WHERE reg_date LIKE :reg_date")
    suspend fun retrieveSingleVehicleData(reg_date: String) : VehiclesData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRequestedVehicles(userData : List<Vehicle>)


    @Query("SELECT * FROM request_vehicle_tbl")
    fun retrieveVehicleRequested() : LiveData<List<Vehicle>>

    @Query("SELECT * FROM request_vehicle_tbl")
   suspend  fun retrieveSingleVehicle() : Vehicle


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleVehicleModel(data: VehicleModel) : Long

    @Query("SELECT * FROM vehicle_model_tbl")
    suspend fun retrieveListSingleModel() : List<VehicleModel>

}