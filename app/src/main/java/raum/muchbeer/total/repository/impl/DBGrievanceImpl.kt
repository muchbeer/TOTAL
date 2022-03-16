package raum.muchbeer.total.repository.impl

import BpapDetailModel
import CgrievanceModel
import DattachmentModel
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.total.db.engagedao.EngagementDao
import raum.muchbeer.total.db.grievancedao.AgrievanceGeneralDao
import raum.muchbeer.total.db.grievancedao.BpapDetailDao
import raum.muchbeer.total.db.grievancedao.CgrievanceDao
import raum.muchbeer.total.db.grievancedao.DattachDao
import raum.muchbeer.total.db.hsedao.HseDao
import raum.muchbeer.total.db.vehicledao.VehicleDao
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.hse.HseModel
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.model.vehicle.VehicleModel
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.Vehicle
import raum.muchbeer.total.repository.datasource.DBGrievanceSource

class DBGrievanceImpl(val aGrievDao : AgrievanceGeneralDao, val bGrievIDao : BpapDetailDao,
                      val cGrievEntrDao: CgrievanceDao, val dAttachDao : DattachDao, val hseDao: HseDao,
                      val engageDao : EngagementDao, val vehiclesDao: VehicleDao
) : DBGrievanceSource {


  //****************************AGrievance****************************************
    override suspend fun insertASingleGriev(data: AgrienceModel): Long {
    return aGrievDao.insertSingleGrievanceGen(data)   }

    override suspend fun inserAtListGriev(data: List<AgrienceModel>) {
    aGrievDao.insertGrievanceList(data)    }

    override fun retrievAgrieveList(): Flow<List<AgrienceModel>> {
      return  aGrievDao.retrieveAGrievanceGeneral()   }

    override fun retrievAGrievenceWithPrimary(primaryKey: String): Flow<List<AgrienceModel>> {
       return aGrievDao.retrieveAGrievenceWithPrimaryKey(primaryKey = primaryKey)     }



    //******************BGrievance******************************
    override fun retrievBGrievDetailD(): Flow<List<BpapDetailModel>> {
      return bGrievIDao.retrieveBpapDetailD()     }

    override suspend fun insertBSingleGrievDetailD(data: BpapDetailModel): Long {
     return bGrievIDao.insertSinglePapDetailD(data)    }

    override suspend fun insertBListGrievDetailD(data: List<BpapDetailModel>) {
      bGrievIDao.insertBpapDetailIDList(data)    }

    override fun retrievBpapDetailDFromPrimaryKey(primaryKey: String): Flow<List<BpapDetailModel>> {
      return  bGrievIDao.retrieveBpapDetailDFromPrimaryKey(primaryKey = primaryKey)   }

    override fun retrievBPapsEnteredWithUserName(username: String): Flow<List<BpapDetailModel>> {
       return bGrievIDao.retrievePapsEnteredWithUserName(username)
    }

    //**************************************CGrievance********************************
    override suspend fun insertCSingleGrievEntries(data: CgrievanceModel): Long {
    return cGrievEntrDao.insertSingleGrievancee(data)   }

    override suspend fun insertcListGrievanceEntries(data: List<CgrievanceModel>) {
     cGrievEntrDao.insertGrievanceees(data)    }

    override fun retrievCGrievEntries(): Flow<List<CgrievanceModel>> {
       return cGrievEntrDao.retrieveAGrievance()    }

    override fun searchCGrieveByName(fullName: String): Flow<List<CgrievanceModel>> {
        return cGrievEntrDao.searchAListOfGrieveWithValuationNo(full_name = fullName)     }

    override suspend fun deleteCGrievance() {
      cGrievEntrDao.clear()    }


    //******************DAttachment**********************************
    override suspend fun insertDSingleAttachment(data: DattachmentModel): Long {
    return dAttachDao.insertDSingleAttachment(data)   }

    override fun retrievDListOfAttachment(): Flow<List<DattachmentModel>> {
       return dAttachDao.retrievDAttachment()     }

    override fun retrievDListOfAttachmentBySelect(checkStatus: Boolean): Flow<List<DattachmentModel>> {
       return dAttachDao.retrievDListOfAttachmentBySelect(checkStatus = checkStatus)     }

    override fun retrievDListOfAttachmentByValuationNo(valuation_no: String): Flow<List<DattachmentModel>> {
        return dAttachDao.retrievDListOfAttachmentByValuationNo(valuation_no)   }

    override suspend fun updateDAttachment(attachment: DattachmentModel) {
        dAttachDao.updateDAttachment(attachment = attachment)
    }


    //HSE DAO
    override suspend fun insertIntoHSE(data: Hsedata) : Long {
      return  hseDao.insertSingleHse(data)
    }

    override fun retrieveLiveHSE(): LiveData<List<Hsedata>> {
     return hseDao.retrieveHse()   }

    override suspend fun retrieveListHSe(): List<Hsedata> {
     return hseDao.retrieveList()    }

    override suspend fun insertIntoSingeHSEModel(data: HseModel): Long {
       return hseDao.insertSingleHseModel(data)
    }



    override suspend fun retrieveListofHseModel(): List<HseModel> {
       return hseDao.retrieveHseModel()
    }

    override suspend fun retrieveSingleHSE(reg_date: String): Hsedata {
        return hseDao.retrieveSingleHSE(reg_date)
    }

    override suspend fun insertIntoEngagement(data: EngageModel): Long {
        return engageDao.insertSingleEngage(data)
    }

    override fun retrieveLiveEngagement(): LiveData<List<EngageModel>> {
     return engageDao.retrieveEngage()    }

    override suspend fun retrieveListEngagement(): List<EngageModel> {
      return engageDao.retrievEngageList()   }

    override suspend fun insertIntoVehicle(data: VehiclesData): Long {
        return vehiclesDao.insertSingleVehicle(data)
    }

    override fun retrieveLiveVehicle(): LiveData<List<VehiclesData>> {
     return vehiclesDao.retrieveVehicle()   }

    override suspend fun retrieveListVehicle(): List<VehiclesData> {
     return vehiclesDao.retrieveList()   }

    override suspend fun insertIntoVehicleModel(data: VehicleModel): Long {
        return vehiclesDao.insertSingleVehicleModel(data)
    }

    override suspend fun insertIntoSingleVehicleDataOG(data: VehiclesData): Long {
        return vehiclesDao.insertSingleVehicle(data)
    }

    override suspend fun retrieveFromSingleVehicleDataOG(reg_date: String): VehiclesData {
       return vehiclesDao.retrieveSingleVehicleData(reg_date)
    }

    override suspend fun retrieveListVehicleModel(): List<VehicleModel> {
     return vehiclesDao.retrieveListSingleModel()    }

    override suspend fun retrieveSingleVehicle(): Vehicle {
        return vehiclesDao.retrieveSingleVehicle()
    }

    override suspend fun insertSingleVehicleData(data: Vehicle): Long {
        return vehiclesDao.insertSingleVehicleData(data)
    }


    override fun retrieveLiveVehicleRequested(): Flow<List<Vehicle>> {
        return vehiclesDao.retrieveVehicleRequested()
    }

    override suspend fun insertIntoListVehicleRequested(data: List<Vehicle>) {
     return vehiclesDao.insertRequestedVehicles(data)    }

    override suspend fun deleteAllPrevRequestVehicle() {
        vehiclesDao.deleteAllVehicles()
    }

    override suspend fun updateDImageAttachment(imageUrl: String, fileName : String) {
        dAttachDao.updateDImageAttachment(imageUrl, fileName)
    }
}