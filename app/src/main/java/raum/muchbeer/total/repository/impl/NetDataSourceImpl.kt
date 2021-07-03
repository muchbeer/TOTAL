package raum.muchbeer.total.repository.impl

import android.content.Intent
import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import raum.muchbeer.total.api.DataService
import raum.muchbeer.total.repository.datasource.NetDataSource
import javax.inject.Inject

class NetDataSourceImpl @Inject constructor(private val dataService: DataService)  : NetDataSource {
/*    suspend fun sendData(): DataModel {
        lateinit var listOfData : DataModel

        try {
            listOfData  =
                dataService.sendData()
            Log.d("DataValues", "retrieve from network: ${listOfData}")
        }catch (e: Exception) {
            Log.d("NetworkLog", "error is ${e.message}")

        }
        return listOfData
    }*/

    fun sendDataFromInput(jsonObject : String ) {
        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObject.toRequestBody("application/json".toMediaTypeOrNull())

    }
    override suspend fun sendData()  {
        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("api_key", "YTc4YWY3ZWVlZWViODU3YTY5ODUzNTA4ZGU3YzVhYzM1NTdjNjM1MWEyYzA1ODU1ZDhlqA12AjkP0qNTc4ZjU3N2QwMDVmMw==")
        jsonObject.put("full_name", "George Kamala")
        jsonObject.put("address","Kinyerezi")
        jsonObject.put("phone_number","255763038743")
        jsonObject.put("district","Ilala")
        jsonObject.put("compasation","MHOLA")
        jsonObject.put("photolink","https://waterlily.co.tz/assets/img/logo.png")
        jsonObject.put("amount","123456")
        jsonObject.put("agreetosign","No")
        jsonObject.put("satisfiedwithcontract","Yes")
        jsonObject.put("anyrecomendations","No")
        jsonObject.put("user_name","Amanu")
        jsonObject.put("reg_date","2020-03-02 09:32:19")

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())


        try {
            val response = dataService.sendData(requestBody)
            if (response.isSuccessful) {
                Log.d("DataValues", "data successful sent:")
                val outputResponse = response.body()

            //    Log.d(" JSON :", outputResponse?.charStream().toString())

                // Convert raw JSON to pretty JSON using GSON library
                val gson = GsonBuilder().setPrettyPrinting().create()
                val prettyJson = gson.toJson(JsonParser.parseString(response.body()?.string())
                                 )
                Log.d("Pretty Printed JSON ", prettyJson)


            }

        }catch (e: Exception) {
            Log.d("NetworkLog", "error is ${e.message}")

        }
    }

    override suspend fun sendLoginGrievance() {
        TODO("Not yet implemented")
    }
}