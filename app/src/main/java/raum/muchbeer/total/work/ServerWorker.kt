package raum.muchbeer.total.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.db.DataDB
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.grievance.BpapDetailModel
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.DattachmentModel
import raum.muchbeer.total.repository.Repository
import javax.inject.Inject

@HiltWorker
class ServerWorker @AssistedInject constructor (@Assisted val appContext: Context,
                                                @Assisted workerParams: WorkerParameters,
                                                val repository: Repository) :
             CoroutineWorker(appContext, workerParams) {

  /*  @Inject
   private lateinit var repository : Repository*/

    override suspend fun doWork(): Result {

        return try {
            try {

                Log.d("ServerWorker", "WorkManager Started")
                repository.sampleWorkerPass()
                val database = DataDB.getDatabaseInstance(appContext)
                //  repository.insertGrievanceToserver(it)

                 database.AgrievanceDao().retrieveListGrievance().forEach {
                     Log.d("ServerWorker", "WorkManager Started for AgrienceModel")
                     repository.insertGrievanceToserver(it)
                 }
                database.HseDao().retrieveHseModel().forEach {
                    Log.d("ServerWorker", "WorkManager Started for HseModel")
                    repository.insertHseModelToServer(it)
                }
                database.EngageDao().retrievEngageList().forEach {
                    Log.d("ServerWorker", "WorkManager Started for EngageModel")
                    repository.insertEngagementToServer(it)
                }
                database.VehicleDao().retrieveListSingleModel().forEach {
                    Log.d("ServerWorker", "WorkManager Started for VehicleModel")
                    repository.insertVehicleModelToServer(it)
                }

                repository.uploadingPicture()

                grievanceOldData(database.CgrievanceDao().retrieveListOfGrievance(),
                      database.DattachDao().retrieveListOfAttachment(), repository)


                Result.success()
            } catch (e: Exception) {
                Log.d("ServerWorker", "exception in doWork ${e.message}")
                Result.failure()
            }
        } catch (e: Exception) {
            Log.d("ServerWorker", "exception in doWork ${e.message}")
            Result.failure()
        }
    }

    private suspend fun grievanceOldData(grievance : List<CgrievanceModel>, attach : List<DattachmentModel>,
                                repository: Repository) = supervisorScope {
        val sharedPreference =  appContext.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
        val valuation_id = sharedPreference.getString("valuation_id", "default")
        val username =  sharedPreference.getString("username","default")
        val field_id = sharedPreference.getString("field_id","default")

        val bpapDetails2 = BpapDetailModel("${valuation_id}", grievance,
            attach)
            if(username =="george") {
                val agrienceOldData = AgrienceModel("200002", "${BuildConfig.API_KEY_GRIEVANCE}",
                    "${field_id}", "${username}", listOf(bpapDetails2 ))
              launch {  repository.insertGrievanceToserver(agrienceOldData)}
            } else  if(username =="george2") {
                    val agrienceOldData = AgrienceModel("200003", "${BuildConfig.API_KEY_GRIEVANCE}",
                        "${field_id}", "${username}", listOf(bpapDetails2 ))
                launch {  repository.insertGrievanceToserver(agrienceOldData)}
            }
            else   if(username =="0678811856") {
                val agrienceOldData = AgrienceModel("200004", "${BuildConfig.API_KEY_GRIEVANCE}",
                    "${field_id}", "${username}", listOf(bpapDetails2 ))
                launch {  repository.insertGrievanceToserver(agrienceOldData)}
            } else   if(username =="george4") {
                val agrienceOldData = AgrienceModel("200005", "${BuildConfig.API_KEY_GRIEVANCE}",
                    "${field_id}", "${username}", listOf(bpapDetails2 ))
                launch {  repository.insertGrievanceToserver(agrienceOldData)}
            } else   if(username =="george5") {
                val agrienceOldData = AgrienceModel("200006", "${BuildConfig.API_KEY_GRIEVANCE}",
                    "${field_id}", "${username}", listOf(bpapDetails2 ))
                launch {  repository.insertGrievanceToserver(agrienceOldData)}
            } else  if(username =="george6") {
                val agrienceOldData = AgrienceModel("200007", "${BuildConfig.API_KEY_GRIEVANCE}",
                    "${field_id}", "${username}", listOf(bpapDetails2 ))
                launch {  repository.insertGrievanceToserver(agrienceOldData)}
            } else {
                return@supervisorScope
            }

    }
}