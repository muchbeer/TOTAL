package raum.muchbeer.total.repository.datasource

import androidx.lifecycle.LiveData
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.grievance.BpapDetailModel
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.DattachmentModel
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.Vehicle

interface DBGrievanceSource {

    fun retrieveGrievanceListLive() : LiveData<List<AgrienceModel>>

    suspend fun insertSingleGriev(data : AgrienceModel) : Long

    suspend fun insertListGriev(data: List<AgrienceModel>)

    suspend fun retrieveAgrieveList() : List<AgrienceModel>

    //******BGrieveID***********8888
    fun retrieveBGrievDetailD() : LiveData<List<BpapDetailModel>>

    suspend fun insertSingleBGrievDetailD(data : BpapDetailModel) : Long

    suspend fun insertListBGrievDetailD(data : List<BpapDetailModel>)

    //******CGrieveEntry***********
    fun retrieveCGrievEntries() : LiveData<List<CgrievanceModel>>

    suspend fun insertSingleCGrievEntries(data : CgrievanceModel) : Long

    suspend fun insertListCGrievanceEntries(data : List<CgrievanceModel>)

    suspend fun retrieveSingleGriev() : CgrievanceModel

    suspend fun deleteGTable()

    //******Dattachment***********8888
    fun retrieveDAttachment() : LiveData<List<DattachmentModel>>

    suspend fun insertSingleDAttachment(data : DattachmentModel) : Long

    suspend fun insertListCAttachment(data : List<DattachmentModel>)

    suspend fun retrieveSingleAttach() : DattachmentModel

    suspend fun deleteDTable()

    //********************8888HSE
    suspend fun insertIntoHSE(data : Hsedata) : Long

    fun retrieveLiveHSE() : LiveData<List<Hsedata>>

    suspend fun retrieveListHSe() : List<Hsedata>

    //********************8888HSE
    suspend fun insertIntoEngagement(data : EngageModel) : Long

    fun retrieveLiveEngagement() : LiveData<List<EngageModel>>

    suspend fun retrieveListEngagement() : List<EngageModel>

    //********************VEHICLE
    suspend fun insertIntoVehicle(data : VehiclesData) : Long

    fun retrieveLiveVehicle() : LiveData<List<VehiclesData>>

    suspend fun retrieveListVehicle() : List<VehiclesData>

    //********************REQUESTED VEHICLE
  //  suspend fun insertIntoVehicle(data : VehiclesData) : Long

    fun retrieveLiveVehicleRequested() : LiveData<List<Vehicle>>

    suspend fun insertIntoListVehicleRequested(data: List<Vehicle>)

}