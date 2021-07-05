package raum.muchbeer.total.work

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import raum.muchbeer.total.db.DataDB
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

    private fun doYourTask() {

    }
}