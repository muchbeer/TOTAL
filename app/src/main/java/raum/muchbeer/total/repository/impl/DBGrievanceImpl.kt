package raum.muchbeer.total.repository.impl

import androidx.lifecycle.LiveData
import raum.muchbeer.total.db.engagedao.EngagementDao
import raum.muchbeer.total.db.grievancedao.AgrievanceGeneralDao
import raum.muchbeer.total.db.grievancedao.BpapDetailDao
import raum.muchbeer.total.db.grievancedao.CgrievanceDao
import raum.muchbeer.total.db.grievancedao.DattachDao
import raum.muchbeer.total.db.hsedao.HseDao
import raum.muchbeer.total.db.vehicledao.VehicleDao
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.grievance.BpapDetailModel
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.DattachmentModel
import raum.muchbeer.total.model.hse.HseModel
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.model.vehicle.VehicleModel
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.Vehicle
import raum.muchbeer.total.repository.datasource.DBGrievanceSource

class DBGrievanceImpl(val grievDao : AgrievanceGeneralDao, val bGrievIDao : BpapDetailDao,
                      val cGrievEntrDao: CgrievanceDao, val dAttachDao : DattachDao, val hseDao: HseDao,
                      val engageDao : EngagementDao, val vehiclesDao: VehicleDao
) : DBGrievanceSource {


    override fun retrieveGrievanceListLive(): LiveData<List<AgrienceModel>> {
    return  grievDao.retrieveGrievanceGeneral()    }

    override suspend fun insertSingleGriev(data: AgrienceModel): Long {
    return grievDao.insertSingleGrievanceGen(data)   }

    override suspend fun insertListGriev(data: List<AgrienceModel>) {
    grievDao.insertGrievanceList(data)    }

    override suspend fun retrieveAgrieveList(): List<AgrienceModel> {
     return grievDao.retrieveListGrievance()    }

    override fun retrieveBGrievDetailD(): LiveData<List<BpapDetailModel>> {
    return bGrievIDao.retrieveBpapDetailD()    }

    override suspend fun insertSingleBGrievDetailD(data: BpapDetailModel): Long {
     return bGrievIDao.insertSinglePapDetailD(data)    }

    override suspend fun insertListBGrievDetailD(data: List<BpapDetailModel>) {
      bGrievIDao.insertBpapDetailIDList(data)    }

    override fun retrieveCGrievEntries(): LiveData<List<CgrievanceModel>> {
    return cGrievEntrDao.retrieveGrievancee()    }

    override suspend fun insertSingleCGrievEntries(data: CgrievanceModel): Long {
    return cGrievEntrDao.insertSingleGrievancee(data)   }

    override suspend fun insertListCGrievanceEntries(data: List<CgrievanceModel>) {
     cGrievEntrDao.insertGrievanceees(data)    }

    override suspend fun retrieveSingleGriev(): CgrievanceModel {
    return cGrievEntrDao.retrieveSingleGrieve()    }

    override suspend fun deleteGTable() {
    cGrievEntrDao.clear()
    }

    override fun retrieveDAttachment(): LiveData<List<DattachmentModel>> {
     return dAttachDao.retrieveAttachment()   }

    override suspend fun insertSingleDAttachment(data: DattachmentModel): Long {
    return dAttachDao.insertSingleAttachment(data)   }

    override suspend fun insertListCAttachment(data: List<DattachmentModel>) {
    return dAttachDao.insertAttachmentList(data)    }

    override suspend fun retrieveSingleAttach(): DattachmentModel {
     return dAttachDao.retrieveSingleAttachment()   }

    override suspend fun deleteDTable() {
        return dAttachDao.clear()    }


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

    override suspend fun retrieveSingleHSE(): Hsedata {
        return hseDao.retrieveSingleHSE()
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

    override suspend fun retrieveFromSingleVehicleDataOG(): VehiclesData {
       return vehiclesDao.retrieveSingleVehicleData()
    }

    override suspend fun retrieveListVehicleModel(): List<VehicleModel> {
     return vehiclesDao.retrieveListSingleModel()    }

    override suspend fun retrieveSingleVehicle(): Vehicle {
        return vehiclesDao.retrieveSingleVehicle()
    }

    override suspend fun insertSingleVehicleData(data: Vehicle): Long {
        return vehiclesDao.insertSingleVehicleData(data)
    }


    override fun retrieveLiveVehicleRequested(): LiveData<List<Vehicle>> {
        return vehiclesDao.retrieveVehicleRequested()
    }

    override suspend fun insertIntoListVehicleRequested(data: List<Vehicle>) {
     return vehiclesDao.insertRequestedVehicles(data)    }
}