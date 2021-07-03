package raum.muchbeer.total.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.api.DataService
import raum.muchbeer.total.datastore.UserDataPref
import raum.muchbeer.total.db.hsedao.HseDao
import raum.muchbeer.total.model.DataState
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.DattachmentModel
import raum.muchbeer.total.model.grievance.papform.PapEntity
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel
import raum.muchbeer.total.model.grievance.papform.PapEntryModel
import raum.muchbeer.total.model.hse.HseModel
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.model.users.GrievanceCredentialEntity
import raum.muchbeer.total.model.users.UserModel
import raum.muchbeer.total.repository.datasource.DBGrievanceSource
import raum.muchbeer.total.repository.datasource.DBPapUserSource
import raum.muchbeer.total.repository.datasource.NetDataSource
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class Repository  @Inject constructor(val dbGgriev: DBGrievanceSource,
                                      val dbSource: DBPapUserSource,
                                      onlineDataSource : NetDataSource, @ApplicationContext val context: Context,
                                      @Named("grievance") val grievanceService: DataService,
                                      @Named("paplist") val paplistService: DataService,
                                      @Named("hse") val hseService: DataService,
                                      @Named("engage") val engageService : DataService,
                                      @Named("gravitee") val generalGriev : DataService,
                                      val userDataPref : UserDataPref
) {

    val gson = Gson()
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()

    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
    var editor = sharedPreference.edit()


    val papListLiveData = dbSource.retrievePapListLive()

    val cgrievanceLiveData = dbGgriev.retrieveCGrievEntries()

    val hseDataLive = dbGgriev.retrieveLiveHSE()

    suspend fun insertToHse(data: Hsedata) :Long {
        return dbGgriev.insertIntoHSE(data)
    }

    suspend fun insertToEngagement(data: EngageModel) : Long {
        return dbGgriev.insertIntoEngagement(data)
    }

    suspend fun receiveEngageList(): List<EngageModel> {
        return dbGgriev.retrieveListEngagement()
    }

    suspend fun retrieveEngageLive() : LiveData<List<EngageModel>> {
        return dbGgriev.retrieveLiveEngagement()
    }
     suspend fun receiveHseList() : List<Hsedata> {
        return dbGgriev.retrieveListHSe()
    }

    suspend fun executeLogin(credentialEntity: GrievanceCredentialEntity) : Flow<DataState<UserModel>> = flow {
        emit(DataState.Loading)

        val jsonDBListPretty: String = gsonPretty.toJson(credentialEntity)
        Log.d("GrievanceModel", "GrievenceModel is : ${jsonDBListPretty}")

        val requestBody = jsonDBListPretty.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = grievanceService.sendLoginGrievance(requestBody)
            if (response.isSuccessful) {
                Log.d("Repository", "grievancce user login successful sent and received:")

                val prettyJson = gsonPretty.toJson(
                    JsonParser.parseString(response.body()?.string())
                )
                val user = gson.fromJson(prettyJson, UserModel::class.java)

                if (user.status == "200") {
                    Log.d("Repository", "value of name user: ${user.full_name}")
                    Log.d("Repository", "The value after login of field ID is ${user.field_id}")

                    editor.putString("field_id","${user.field_id}")
                    editor.putString("username", "${user.full_name}")
                    editor.putString("status", "${user.status}")
                    editor.putString("position", "${user.position}")
                    editor.commit()
                    val papListModel = PapEntity(
                        BuildConfig.API_KEY,
                        user.field_id
                    )
                    receivePapList(papListModel = papListModel)
                    emit(DataState.Success(user))
                } else if (user.status == "204") {
                    emit(DataState.Error("Fail to Login, Please try again"))
                    Log.d("Repository", "The validation is: 204 and Invalid is called")
                }
            }

        }catch (e: Exception) {
            Log.d("Repository", "error after login is clicked is ${e.message}")
                     emit(DataState.ErrorException(e))        }
    }

    suspend fun insertGrievanceToserver(agriev : AgrienceModel) {
        val jsonDBAgriev : String = gsonPretty.toJson(agriev)
        Log.d("Repository", "Ping json all grievence to give you ${jsonDBAgriev}")

        val requestBody = jsonDBAgriev.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = generalGriev.sendGrievanceDataToServer(requestBody)
            if (response.isSuccessful) {
                val gson2 = GsonBuilder().setPrettyPrinting()
                    .create()
                val prettyJson = gson2.toJson(
                    JsonParser.parseString(response.body()?.string())
                )
                Log.d("Repository", "Retrieve response from Grievance Server ${prettyJson}")
            }
        }catch (e: Exception) {
            Log.d("Repository", "Network after clicked grievanceForm error is ${e.message}")
        }
    }

    suspend fun insertEngagementToServer(engagement : EngageModel) {
        val jsonDBListPretty: String = gsonPretty.toJson(engagement)
        Log.d("Repository", "Ping json engagment to give you ${jsonDBListPretty}")
        val requestBody = jsonDBListPretty.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = engageService.sendEngageDataToServer(requestBody)
            if (response.isSuccessful) {
                val gson2 = GsonBuilder().setPrettyPrinting()
                    .create()
                val prettyJson = gson2.toJson(
                    JsonParser.parseString(response.body()?.string())
                )
                Log.d("Repository", "Retrieve response from engage Server ${prettyJson}")
            }
        }catch (e: Exception) {
            Log.d("Repository", "Network after clicked engagementData error is ${e.message}")
        }
    }

    suspend fun insertHseToServer(hseData : Hsedata) {
        val jsonDBListPretty: String = gsonPretty.toJson(hseData)
        Log.d("Repository", "Pink the login credential by get field ID${jsonDBListPretty}")
        val fieldId = sharedPreference.getString("field_id","2016")
        val username = sharedPreference.getString("username", "default")
        val hseModel = HseModel("${BuildConfig.API_KEY_GRIEVANCE}", "${fieldId}",
             "${username}",
                        listOf(hseData))
        val overhsegeneral = gsonPretty.toJson(hseModel)
        Log.d("Repository", "Sending to the hse server receives: ${overhsegeneral}")
        val hseModelToJson : String = gsonPretty.toJson(hseModel)

        val requestBody = hseModelToJson.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = hseService.sendHseDataToServer(requestBody)
            if(response.isSuccessful) {
                val gson2 = GsonBuilder().setPrettyPrinting()
                    .create()
                val prettyJson = gson2.toJson(
                    JsonParser.parseString(response.body()?.string())
                )
                Log.d("Repository", "Retrieve response from hseServer ${prettyJson}")
            }
        } catch (e: Exception) {
            Log.d("Repository", "Network after clicked retrievedData error is ${e.message}")
        }
    }

    suspend fun receivePapList( papListModel: PapEntity) {

     //   emit(PapListStateEvent.Loading)
        val jsonDBListPretty: String = gsonPretty.toJson(papListModel)
        Log.d("Repository", "Pink the login credential by get field ID${jsonDBListPretty}")

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonDBListPretty.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = paplistService.sendData(requestBody)
            if (response.isSuccessful) {
                Log.d("Repository", "RetrieveData function clicked and paplist successful sent:")

                // Convert raw JSON to pretty JSON using GSON library
                val gson2 = GsonBuilder().setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
                val prettyJson = gson2.toJson(
                    JsonParser.parseString(response.body()?.string())
                )

                val jsonObject = JSONObject(prettyJson)
                val jsonArray = jsonObject.getJSONArray("paplist")
                Log.d("Repository", "paplist are : ${jsonArray}")
                for (i in 0 until jsonArray.length()) {
                    val val_id = "${jsonArray.getJSONObject(i).getString("valuation_number")}"
                  val papName =  "${jsonArray.getJSONObject(i).getString("full_name")}"
                  //   userDataPref.storeValuationId("${val_id}")


                    val papEntry = PapEntryListModel("${jsonArray.getJSONObject(i).getString("valuation_number")}",
                        "${jsonArray.getJSONObject(i).getString("full_name")}",
                        "${jsonArray.getJSONObject(i).getString("phone_number")}",
                        "${jsonArray.getJSONObject(i).getString("district")}",
                        "${jsonArray.getJSONObject(i).getString("region")}")

                    val checkRecord =  dbSource.insertSinglePap(papEntry)
                    if(checkRecord>-1) {
                        PapListStateEvent.getListOfPap(papEntry)
                        Log.d("Repository", "Record inserted successfull")
                    }
                    Log.d("Repository", "value of name user: ${jsonArray.getJSONObject(i).getString("valuation_number")}")
                }

                val user = gsonPretty.fromJson(prettyJson, PapEntryModel::class.java)
                Log.d("Repository", "PapListItem Item is : ${user.statusDesc}")

            }

        }catch (e: Exception) {
            Log.d("Repository", "Network after clicked retrievedData error is ${e.message}")
            PapListStateEvent.Error(e.toString())
        }
    }

    suspend fun insertToCgrienvance(grievanceModel: CgrievanceModel) : Long {
       return dbGgriev.insertSingleCGrievEntries(grievanceModel)
    }

    suspend fun insertToDAttachment(attachment: DattachmentModel) : Long {
        return dbGgriev.insertSingleDAttachment(attachment)
    }

    suspend fun retrieveCgrievance() : CgrievanceModel {
        return dbGgriev.retrieveSingleGriev()
    }

    suspend fun retrieveAttachment() : DattachmentModel { return dbGgriev.retrieveSingleAttach()}

    suspend fun clearGrievance() { dbGgriev.deleteGTable()}

    suspend fun clearAttachment() { dbGgriev.deleteDTable()}

    suspend fun retrieveAGrieveGeralList() : List<AgrienceModel>{ return dbGgriev.retrieveAgrieveList()}

    val retrieveModel : LiveData<List<AgrienceModel>> =  dbGgriev.retrieveGrievanceListLive()

    suspend fun insertToAGriev(data : AgrienceModel) : Long {
        return dbGgriev.insertSingleGriev(data)
    }

}

sealed class PapListStateEvent<out T> {
    data class getListOfPap<T>(val data : T) : PapListStateEvent<T>()
    data class Error(val error: String) : PapListStateEvent<Nothing>()
    object Loading : PapListStateEvent<Nothing>()

}