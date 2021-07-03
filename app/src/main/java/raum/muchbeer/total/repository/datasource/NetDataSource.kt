package raum.muchbeer.total.repository.datasource

import okhttp3.ResponseBody

interface NetDataSource {
    suspend fun sendData()
    suspend fun sendLoginGrievance()
}