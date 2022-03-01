package raum.muchbeer.total.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.api.DataService
import raum.muchbeer.total.model.DataState
import raum.muchbeer.total.model.ImageFirestore
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
import raum.muchbeer.total.model.vehicle.VehicleModel
import raum.muchbeer.total.model.vehicle.VehicleState
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.ReceiveVehicle
import raum.muchbeer.total.model.vehicle.request.RequestVehicleModel
import raum.muchbeer.total.model.vehicle.request.Vehicle
import raum.muchbeer.total.repository.datasource.DBGrievanceSource
import raum.muchbeer.total.repository.datasource.DBPapUserSource
import java.io.FileInputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class Repository  @Inject constructor(val dbGgriev: DBGrievanceSource,
                                      val dbSource: DBPapUserSource,
                                      @ApplicationContext val context: Context,
                                      @Named("grievance") val grievanceService: DataService,
                                      @Named("paplist") val paplistService: DataService,
                                      @Named("hse") val hseService: DataService,
                                      @Named("engage") val engageService : DataService,
                                      @Named("gravitee") val generalGriev : DataService,
                                      @Named("requestvehicle") val requestVehicleService: DataService,
                                      @Named("vehicle") val vehicleService : DataService

) {

    val db = Firebase.firestore

    val gson = Gson()
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()

    private val _checkForDataEntry = MutableLiveData<Boolean>()

   private val listPaps = MutableLiveData<List<PapEntryListModel>>()
    private val listVehicles = MutableLiveData<List<Vehicle>>()

    init {
        _checkForDataEntry.postValue(false)
    }

    val sharedPreference = context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
    var editor = sharedPreference.edit()

    private var storageReferenence = FirebaseStorage.getInstance().getReference()

    suspend fun insertingImages(data: ImageFirestore): Long {
        return dbGgriev.insertImages(data)
    }

    suspend fun retrieveGrievancAndSaveToFirestore(agriev: AgrienceModel)  = supervisorScope{

        launch {
            if (_checkForDataEntry.value == true) {
                Log.d("Repository", "No more data entered")
                return@launch
            } else {
                db.collection("grievance").add(agriev)
                    .addOnSuccessListener { documentReference ->
                        _checkForDataEntry.value  = true
                        Log.d("Repository", "Saved data to firestore successfull")
                    }.addOnFailureListener {
                        Log.d("Repository", "Failure with error: ${it.toString()}")
                    }
            }
        }

    }

    suspend fun uploadingPicture() = supervisorScope {

        val sdf = SimpleDateFormat("dd:hh:mm:ss")
        val currentDate = sdf.format(Date())
        val imageRef = storageReferenence.child("images/" + currentDate)
        dbGgriev.retrieveListofImages().forEach { image ->
            Log.d("PhotoFragment: ", "Repository get Image empty url: ${image.imageUrl} ")
            Log.d("PhotoFragment: ", "Repository entered file Name: ${image.fileName} ")

            if (image != null && image.imageUrl == "gadiel") {
                Log.d("PhotoFragment: ", "Repository entered list of images")
                //  val uploadTask = imageRef.putFile(photosUri)
                val inputstream = FileInputStream(image.fileName)
                val uploadTask = imageRef.putStream(inputstream)
                uploadTask.addOnSuccessListener {
                    val downloadUrl = imageRef.downloadUrl
                    downloadUrl.addOnSuccessListener {
                        Log.d("FragmentPhoto", "Image link sent is:   ${it.toString()}")
                        db.collection("images")
                            .add(
                                ImageFirestore(
                                    "${image.fileName}",
                                    "${it.toString()}", "${currentDate}"
                                )
                            ).addOnSuccessListener { documentReference ->
                                //Update the image
                                launch {
                                    dbGgriev.updateImages("${it.toString()}", "${image.fileName}")
                                    Log.d(
                                        "FragmentPhoto",
                                        "In the repository the value is updated succesful"
                                    )
                                }

                                Log.d(
                                    "FragmentPhoto",
                                    "DocumentSnapshot written with ID: ${documentReference.id}"
                                )
                            }
                            .addOnFailureListener { e ->
                                Log.w("FragmentPhoto", "Error adding document", e)
                            }
                    }
                    uploadTask.addOnFailureListener {
                        Log.e("FragmentPhoto", it.message!!)
                    }

                }
            }
        }

    }

    val hseDataLive = dbGgriev.retrieveLiveHSE()

    fun sampleWorkerPass() {
        Log.d("Repository", "This is to confirm that the worker is working at higher level ")
    }

    //***************HSE****************8
    suspend fun insertToHse(data: Hsedata): Long {
        return dbGgriev.insertIntoHSE(data)
    }

    suspend fun insertToHseModel(data: HseModel): Long {
        return dbGgriev.insertIntoSingeHSEModel(data)
    }

    //*********************Engagement********************8
    suspend fun insertToEngagement(data: EngageModel): Long {
        return dbGgriev.insertIntoEngagement(data)
    }

    suspend fun insertEngagementToServer(engagement: EngageModel) {
        val jsonDBListPretty: String = gsonPretty.toJson(engagement)
        Log.d("Repository", "Ping json engagment to give you ${jsonDBListPretty}")
        val requestBody = jsonDBListPretty.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = engageService.sendEngageDataToServer(requestBody)
            if (response.isSuccessful) {
                if (response.body() == null) {
                    return
                }
                val gson2 = GsonBuilder().setPrettyPrinting()
                    .create()
                val prettyJson = gson2.toJson(
                    JsonParser.parseString(response.body()?.string())
                )
                Log.d("Repository", "Retrieve response from engage Server ${prettyJson}")
            }
        } catch (e: Exception) {
            Log.d("Repository", "Network after clicked engagementData error is ${e.message}")
        }
    }

    //*********************Grievance*********************
    suspend fun retrieveListOfGrievance(): List<CgrievanceModel> {
        return dbGgriev.retrieveListOfGrievance()
    }

    val cgrievanceLiveData = dbGgriev.retrieveCGrievEntries()

    val retrieveEngageLive = dbGgriev.retrieveLiveEngagement()

    suspend fun insertGrievanceToserver(agriev: AgrienceModel) {
        val jsonDBAgriev: String = gsonPretty.toJson(agriev)
        Log.d("Repository", "Ping json all grievence to give you ${jsonDBAgriev}")

        val requestBody = jsonDBAgriev.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = generalGriev.sendGrievanceDataToServer(requestBody)
            if (response.isSuccessful) {
                if (response.body() == null) {
                    return
                }
                val gson2 = GsonBuilder().setPrettyPrinting()
                    .create()
                val prettyJson = gson2.toJson(
                    JsonParser.parseString(response.body()?.string())
                )
                Log.d("Repository", "Retrieve response from Grievance Server ${prettyJson}")
            }
        } catch (e: Exception) {
            Log.d("Repository", "Network after clicked grievanceForm error is ${e.message}")
        }
    }

    suspend fun insertToCgrienvance(grievanceModel: CgrievanceModel): Long {
        return dbGgriev.insertSingleCGrievEntries(grievanceModel)
    }

    suspend fun retrieveCgrievance(reg_date: String): CgrievanceModel {
        return dbGgriev.retrieveSingleGriev(reg_date)
    }

    suspend fun retrieveAGrieveGeralList(): List<AgrienceModel> {
        return dbGgriev.retrieveAgrieveList()
    }

    suspend fun insertToAGriev(data: AgrienceModel): Long {
        return dbGgriev.insertSingleGriev(data)
    }


    //********************Attachment**********************

    suspend fun retrieveAttachment(unique_data : String) : DattachmentModel {
        return dbGgriev.retrieveSingleAttach(unique_data)}

    //*******************Vehicle**************************
    suspend fun insertVehicle(data: VehiclesData): Long {
        return dbGgriev.insertIntoVehicle(data)
    }

    suspend fun insertToSingleVehicle(data: VehicleModel): Long {
        return dbGgriev.insertIntoVehicleModel(data)
    }

    suspend fun insertVehicleModelToServer(vehicleModel: VehicleModel) {
        val jsonDBListPretty: String = gsonPretty.toJson(vehicleModel)
        Log.d("Repository", "Ping json vehicles data to give you ${jsonDBListPretty}")
        val requestBody = jsonDBListPretty.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = vehicleService.sendVehicleDataToServer(requestBody)
            if (response.isSuccessful) {
                val gson2 = GsonBuilder().setPrettyPrinting()
                    .create()
                val prettyJson = gson2.toJson(
                    JsonParser.parseString(response.body()?.string())
                )
                Log.d("Repository", "Retrieve response from Vehicles field Server ${prettyJson}")
            }
        } catch (e: Exception) {
            Log.d("Repository", "Network after clicked engagementData error is ${e.message}")
        }
    }

    suspend fun insertVehicleFieldData(vehiclesData: VehiclesData) {
        val jsonDBListPretty: String = gsonPretty.toJson(vehiclesData)
        Log.d("Repository", "Ping json vehicles data to give you ${jsonDBListPretty}")
        val requestBody = jsonDBListPretty.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = vehicleService.sendVehicleDataToServer(requestBody)
            if (response.isSuccessful) {
                val gson2 = GsonBuilder().setPrettyPrinting()
                    .create()
                val prettyJson = gson2.toJson(
                    JsonParser.parseString(response.body()?.string())
                )
                Log.d("Repository", "Retrieve response from Vehicles field Server ${prettyJson}")
            }
        } catch (e: Exception) {
            Log.d("Repository", "Network after clicked engagementData error is ${e.message}")
        }
    }

    val retrieveVehicledataLive = dbGgriev.retrieveLiveVehicle()


    suspend fun insertToSingleVehicleDataOG(data: VehiclesData): Long {
        return dbGgriev.insertIntoSingleVehicleDataOG(data)
    }

    suspend fun retriveFromSingleVehicleDataOG(reg_date: String): VehiclesData {
        return dbGgriev.retrieveFromSingleVehicleDataOG(reg_date)
    }

    suspend fun requestVehicle(): Flow<VehicleState<List<Vehicle>>> = flow {
        val fieldId = sharedPreference.getString("field_id", "2016")
        emit(VehicleState.Loading)

        val receiveVehicle = ReceiveVehicle(
            "${BuildConfig.API_KEY_GRIEVANCE}",
            "${fieldId}"
        )

        val jsonDBListPretty: String = gsonPretty.toJson(receiveVehicle)
        Log.d("Repository", "Repository Vehicle json is : ${jsonDBListPretty}")

        val requestBody = jsonDBListPretty.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = requestVehicleService.requestVehicleFromServer(requestBody)
            if (response.isSuccessful) {
                val prettyJson = gsonPretty.toJson(
                    JsonParser.parseString(response.body()?.string())
                )
                val vehiclesModel = gson.fromJson(prettyJson, RequestVehicleModel::class.java)

                if (vehiclesModel.status == "200") {
                    Log.d(
                        "Repository",
                        "The Desc success return Description ID is ${vehiclesModel.statusDesc}"
                    )


                    val listOfVehicle: List<Vehicle> = vehiclesModel.vehicles
                   // dbGgriev.insertIntoListVehicleRequested(listOfVehicle)
                    listVehicles.postValue(listOfVehicle)
                    emit(VehicleState.Success(listOfVehicle))
                }
            }
        } catch (e: IOException) {
            Log.d("Repository", "error after login is vehicles Description is ${e.message}")
            emit(VehicleState.Error(e.message.toString()))
        }

    }

    fun getVehicleListFlow() = networkBoundResource(
            query =   {
                dbGgriev.retrieveLiveVehicleRequested()
            },
            fetch = {
                listVehicles.value
            },
            saveFetchResult = {
                dbGgriev.deleteAllPrevRequestVehicle()
                dbGgriev.insertIntoListVehicleRequested(it!!)
            }
    )

    //*********************Login
    suspend fun executeLogin(credentialEntity: GrievanceCredentialEntity): Flow<DataState<UserModel>> =
        flow {
            emit(DataState.Loading)

            val jsonDBListPretty: String = gsonPretty.toJson(credentialEntity)
            Log.d("GrievanceModel", "GrievenceModel is : ${jsonDBListPretty}")

            val requestBody = jsonDBListPretty.toRequestBody("application/json".toMediaTypeOrNull())

            try {
                val response = grievanceService.sendLoginGrievance(requestBody)
                if (response.isSuccessful) {

                    if (response.body() ==null) {
                        emit(DataState.Error("Please check your internet and Try again"))
                        return@flow
                    } else if (response.code() !=200) {
                        emit(DataState.Error("Please check your server and Try again"))
                        return@flow
                    }

                    Log.d("Repository", "grievancce user login successful sent and received:")

                    val prettyJson = gsonPretty.toJson(
                        JsonParser.parseString(response.body()?.string())
                    )
                    val user = gson.fromJson(prettyJson, UserModel::class.java)

                    if (user.status == "200") {
                        Log.d("Repository", "value of name user: ${user.full_name}")
                        Log.d("Repository", "The value after login of field ID is ${user.field_id}")

                        editor.putString("field_id", "${user.field_id}")
                        editor.putString("username", "${user.full_name}")
                        editor.putString("status", "${user.status}")
                        editor.putString("position", "${user.position}")
                        editor.apply()
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


                } else {
                    emit(DataState.Error("Please check your network"))
                }

            } catch (e: Exception) {
                Log.d("Repository", "error after login is clicked is ${e.message}")
                emit(DataState.ErrorException(e))
            }
        }

    suspend fun receivePapList(papListModel: PapEntity)  {

     //   emit(DataState.Loading)

        val jsonDBListPretty: String = gsonPretty.toJson(papListModel)
        Log.d("Repository", "Pink the login credential by get field ID${jsonDBListPretty}")

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonDBListPretty.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = paplistService.sendData(requestBody)
            if (response.isSuccessful) {

                if (response.body() == null) {
                    PapListStateEvent.Error("Please check your network")
                    return
                }
                Log.d("Repository", "RetrieveData function clicked and paplist successful sent:")

                // Convert raw JSON to pretty JSON using GSON library
                val gson2 = GsonBuilder().setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation()
                    .create()
                val prettyJson = gson2.toJson(
                    JsonParser.parseString(response.body()?.string())
                )

                    val papResult = gson2.fromJson(prettyJson, PapEntryModel::class.java)
                  listPaps.postValue(papResult.paplist)
                Log.d("Repository", "Result Description after login : ${papResult.statusDesc}")
            papResult.paplist.forEach { papEntry ->
             val checkRecord =   dbSource.insertSinglePap(papEntry)


                if (checkRecord > -1) {
                    PapListStateEvent.Success(papEntry)
                    Log.d("Repository", "Record inserted successfull")
                }
            }

              /*  val user = gsonPretty.fromJson(prettyJson, PapEntryModel::class.java)
                Log.d("Repository", "PapListItem Item is : ${user.statusDesc}")*/

/*                val jsonObject = JSONObject(prettyJson)
                val jsonArray = jsonObject.getJSONArray("paplist")
                Log.d("Repository", "paplist are : ${jsonArray}")
                val papEntity = mutableListOf<PapEntryListModel>()
                for (i in 0 until jsonArray.length()) {
                    val val_id = "${jsonArray.getJSONObject(i).getString("valuation_number")}"
                    val papName = "${jsonArray.getJSONObject(i).getString("full_name")}"
                    //   userDataPref.storeValuationId("${val_id}")


                    val papEntry = PapEntryListModel(
                        "${jsonArray.getJSONObject(i).getString("valuation_number")}",
                        "${jsonArray.getJSONObject(i).getString("full_name")}",
                        "${jsonArray.getJSONObject(i).getString("phone_number")}",
                        "${jsonArray.getJSONObject(i).getString("district")}",
                        "${jsonArray.getJSONObject(i).getString("region")}"
                    )


                    papEntity.add(papEntry)

                //    emit(DataState.Success(listPaps))
                    val checkRecord = dbSource.insertSinglePap(papEntry)
                    if (checkRecord > -1) {
                        PapListStateEvent.getListOfPap(papEntry)
                        Log.d("Repository", "Record inserted successfull")
                    }

                }*/

            }

        } catch (e: Exception) {
            Log.d("Repository", "Network after clicked retrievedData error is ${e.message}")
            PapListStateEvent.Error(e.toString())
           // emit(DataState.ErrorException(e))
        }
    }

    fun getPapListFlow() = networkBoundResource(
            query =   {
                dbSource.retrievePapListLive()
            },
            fetch = {
                listPaps.value
            },
            saveFetchResult = {
                dbSource.deletepaps()
                dbSource.insertListPap(it!!)
            }
    )

    //*******************HSE***********************************

    suspend fun insertHseModelToServer(hseModel: HseModel) {
        val overhsegeneral = gsonPretty.toJson(hseModel)
        Log.d("Repository", "Sending to the hse server receives: ${overhsegeneral}")
        val hseModelToJson: String = gsonPretty.toJson(hseModel)

        val requestBody = hseModelToJson.toRequestBody("application/json".toMediaTypeOrNull())

        try {
            val response = hseService.sendHseDataToServer(requestBody)
            if (response.isSuccessful) {
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

    suspend fun insertToHseDataFirst(hsedata: Hsedata): Long {
        return dbGgriev.insertIntoHSE(hsedata)
    }

    suspend fun retrieveSingleHse(reg_date: String): Hsedata {
        return dbGgriev.retrieveSingleHSE(reg_date)
    }

    suspend fun retrieveHseSingleList(): List<Hsedata> {
        return dbGgriev.retrieveListHSe()
    }

    val retrieveHseDataLive: LiveData<List<Hsedata>> = dbGgriev.retrieveLiveHSE()

    suspend fun insertToDAttachment(attachment: DattachmentModel): Long {
        return dbGgriev.insertSingleDAttachment(attachment)
    }

    suspend fun retrieveFromFireStoreData() = supervisorScope {
        launch {
            val docRef = db.collection("grievance").document()
            docRef.get().addOnSuccessListener { result ->
               val agriev = result.toObject(AgrienceModel::class.java)

                Log.d("FireStoreObject", "model is: ${gsonPretty.toJson(agriev)}")

            }.addOnFailureListener{
                Log.d("Firestore", "Error getting documents: ", it)

            }
        }
    }

    fun searchPaps(paps : String) : LiveData<List<PapEntryListModel>>{
        return dbSource.searchPaps(paps)
    }
}
sealed class PapListStateEvent<out T> {
    data class Success<T>(val data : T) : PapListStateEvent<T>()
    data class Error(val error: String) : PapListStateEvent<Nothing>()
    object Loading : PapListStateEvent<Nothing>()

}