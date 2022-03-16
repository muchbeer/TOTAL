package raum.muchbeer.total.repository.datasource

import BpapDetailModel
import CgrievanceModel
import DattachmentModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.hse.HseModel
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.model.vehicle.VehicleModel
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.Vehicle


interface DBGrievanceSource {

    suspend fun insertASingleGriev(data : AgrienceModel) : Long

    suspend fun inserAtListGriev(data: List<AgrienceModel>)

    fun retrievAgrieveList() : Flow<List<AgrienceModel>>

   fun retrievAGrievenceWithPrimary(primaryKey : String) : Flow<List<AgrienceModel>>


    //******BGrieveID***********8888
    fun retrievBGrievDetailD() : Flow<List<BpapDetailModel>>

    suspend fun insertBSingleGrievDetailD(data : BpapDetailModel) : Long

    suspend fun insertBListGrievDetailD(data : List<BpapDetailModel>)

    fun retrievBpapDetailDFromPrimaryKey(primaryKey: String) : Flow<List<BpapDetailModel>>

    fun retrievBPapsEnteredWithUserName(username : String) : Flow<List<BpapDetailModel>>


    //******CGrieveEntry***********

    suspend fun insertCSingleGrievEntries(data : CgrievanceModel) : Long

    suspend fun insertcListGrievanceEntries(data : List<CgrievanceModel>)

     fun retrievCGrievEntries() : Flow<List<CgrievanceModel>>

     fun searchCGrieveByName(fullName: String) : Flow<List<CgrievanceModel>>

    suspend fun deleteCGrievance()



    //******Dattachment***********8888

    suspend fun insertDSingleAttachment(data : DattachmentModel) : Long

    fun retrievDListOfAttachment() : Flow<List<DattachmentModel>>

    fun retrievDListOfAttachmentBySelect(checkStatus : Boolean) : Flow<List<DattachmentModel>>

    fun retrievDListOfAttachmentByValuationNo(valuation_no : String) : Flow<List<DattachmentModel>>

    suspend fun updateDAttachment(attachment: DattachmentModel)

    suspend fun updateDImageAttachment(imageUrl: String, fileName: String)

    //********************HSE DATA **************
    suspend fun insertIntoHSE(data : Hsedata) : Long

    fun retrieveLiveHSE() : LiveData<List<Hsedata>>

    suspend fun retrieveListHSe() : List<Hsedata>

    suspend fun insertIntoSingeHSEModel(data : HseModel) : Long

    suspend fun retrieveListofHseModel() : List<HseModel>

    suspend fun retrieveSingleHSE(reg_date: String) : Hsedata

    //********************Engagement****************
    suspend fun insertIntoEngagement(data : EngageModel) : Long

    fun retrieveLiveEngagement() : LiveData<List<EngageModel>>

    suspend fun retrieveListEngagement() : List<EngageModel>

    //********************VEHICLE
    suspend fun insertIntoVehicle(data : VehiclesData) : Long

    fun retrieveLiveVehicle() : LiveData<List<VehiclesData>>

    suspend fun retrieveListVehicle() : List<VehiclesData>

    suspend fun insertIntoVehicleModel(data : VehicleModel) : Long

    suspend fun insertIntoSingleVehicleDataOG(data: VehiclesData) : Long

    suspend fun retrieveFromSingleVehicleDataOG(reg_date: String) : VehiclesData

    suspend fun retrieveListVehicleModel() : List<VehicleModel>

    suspend fun retrieveSingleVehicle() : Vehicle

    suspend fun insertSingleVehicleData(data: Vehicle) : Long
    //********************REQUESTED VEHICLE

    fun retrieveLiveVehicleRequested() : Flow<List<Vehicle>>

    suspend fun insertIntoListVehicleRequested(data: List<Vehicle>)

    suspend fun deleteAllPrevRequestVehicle()




}