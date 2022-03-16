package raum.muchbeer.total.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.model.vehicle.VehicleModel
import raum.muchbeer.total.model.vehicle.VehicleState
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.Vehicle
import raum.muchbeer.total.repository.Repository
import raum.muchbeer.total.utils.Constant.Companion.DEFAULT_VALUE
import raum.muchbeer.total.utils.Constant.Companion.FIELD_ID_API
import raum.muchbeer.total.utils.Constant.Companion.USER_NAME_API
import raum.muchbeer.total.utils.Constant.Companion.VEHICLE_NUMBER_API
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor (val repository: Repository,
                                            @ApplicationContext context: Context) : ViewModel(){

   val sharedPreference =  context.getSharedPreferences("PREFERENCE_VEHICLE", Context.MODE_PRIVATE)
    val userSharedPreference =  context.getSharedPreferences("PREFERENCE_NAME",Context.MODE_PRIVATE)
     val editor = userSharedPreference.edit()


    private val _navigateToFormFilling = MutableSharedFlow<Vehicle>()
    val navigateToFormFilling: SharedFlow<Vehicle>  = _navigateToFormFilling


    private val _checkDataDB = MutableLiveData<String>()
        val checkDataDB : LiveData<String>
            get() = _checkDataDB

      private val _vehicleRequestState : MutableLiveData<VehicleState<List<Vehicle>>> = MutableLiveData()
      val vehicleRequestState : LiveData<VehicleState<List<Vehicle>>>
        get() = _vehicleRequestState

    private var _inputDistanceCovered = MutableLiveData<String>()
    val observeDistanceCovered = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputDistanceCovered.value = get()
            }
        })
    }


    private var _inputHoursTravelled = MutableLiveData<String>()
    val observeHoursTraveled = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputHoursTravelled.value = get()
            }
        })
    }


    //****************DISPLAY VEHICLES

    //****************LOGIN USER
    fun requestVehicles() = viewModelScope.launch {
        repository.requestVehicle().collect{
                _vehicleRequestState.value = it
        }
    }

    val receiveVehicleRequested = repository.getVehicleListFlow().asLiveData()

    fun displayFormFilling(vehicleList: Vehicle) = viewModelScope.launch {
        _navigateToFormFilling.emit(vehicleList)   }

    fun insertToSingleVehicleModel() = viewModelScope.launch {
        val randomNumber = (100..200000).random()
        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
        val currentDate = sdf.format(Date())
        val vehicleNo =   sharedPreference.getString(VEHICLE_NUMBER_API, DEFAULT_VALUE)
        val user_name = userSharedPreference.getString(USER_NAME_API, DEFAULT_VALUE)
        val fielId = userSharedPreference.getString(FIELD_ID_API, DEFAULT_VALUE)


        val vehicleData = VehiclesData(
            distance_covered = "${_inputDistanceCovered.value}",
            hours_covered = "${_inputHoursTravelled.value}",
            primary_key = "$randomNumber",
            reg_date = currentDate,
            vehicle_number = vehicleNo!!)


    val checkVD =   repository.insertToSingleVehicleDataOG(vehicleData)

       if (checkVD > -1) {
           Log.d("ViewModelVehicel","VehicleModel saved: ${checkVD}")
       }

        val retrieveVD = repository.retriveFromSingleVehicleDataOG(
            reg_date = currentDate)

        Log.d ("VehicleViewModel", "Retrieve USers are : ${retrieveVD}")

        val vehicleModel = VehicleModel(
            api_key = BuildConfig.API_KEY_GRIEVANCE,
              field_id = fielId!!,
            user_name= user_name!!,
            vehicle_key = randomNumber.toString(),
            vehiclesreport =   listOf(retrieveVD))

        val checkVehicleModel = repository.insertToSingleVehicle(vehicleModel)

        if(checkVehicleModel >-1) {
            _checkDataDB.value = "Success"

        }
    }


    val retrieveLiveVehicle = repository.retrieveVehicledataLive
    //save records to database
    fun insertToVehicles()  =  viewModelScope.launch{
        val randomNumber = (100..200000).random()
        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
        val currentDate = sdf.format(Date())

     val vehicleNo =   sharedPreference.getString(VEHICLE_NUMBER_API, DEFAULT_VALUE)

    val vehicleData = VehiclesData(
        distance_covered = "${_inputDistanceCovered.value}",
         hours_covered =   "${_inputHoursTravelled.value}",
         primary_key =     "$randomNumber",
        reg_date = currentDate,
         vehicle_number = vehicleNo!!)

        val checkVehicleEntry = repository.insertVehicle(vehicleData)
        if(checkVehicleEntry >-1) {
            _checkDataDB.value = "Success"
            repository.insertVehicleFieldData(vehicleData)
        }
    }
}


