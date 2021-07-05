package raum.muchbeer.total.viewmodel

import android.content.Context
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel
import raum.muchbeer.total.model.users.GrievanceCredentialEntity
import raum.muchbeer.total.model.vehicle.VehicleModel
import raum.muchbeer.total.model.vehicle.VehicleState
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.RequestVehicleModel
import raum.muchbeer.total.model.vehicle.request.Vehicle
import raum.muchbeer.total.repository.Repository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor (val repository: Repository,
                                            @ApplicationContext context: Context) : ViewModel(){

    val sharedPreference =  context.getSharedPreferences("PREFERENCE_VEHICLE", Context.MODE_PRIVATE)

    private val _navigateToFormFilling = MutableLiveData<Vehicle>()
    val navigateToFormFilling: LiveData<Vehicle>
        get() = _navigateToFormFilling


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

    val receiveVehicleRequested = repository.displayVehicles

    fun displayComplete() {    _navigateToFormFilling.value = null    }

    fun displayFormFilling(vehicleList: Vehicle) {  _navigateToFormFilling.value =vehicleList   }

    fun insertToSingleVehicleModel() = viewModelScope.launch {
        val randomNumber = (100..200000).random()
        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
        val currentDate = sdf.format(Date())
        val vehicleNo =   sharedPreference.getString("vehicle_number", "default")
        val user_name = sharedPreference.getString("username", "default")
        val fielId = sharedPreference.getString("field_id", "default")

        val vehicleData = VehiclesData("${_inputDistanceCovered.value}",
            "${_inputHoursTravelled.value}",
            "${randomNumber}", "${currentDate}",
            "${vehicleNo}")

    val checkVD =   repository.insertToSingleVehicleDataOG(vehicleData)

       if (checkVD > -1) {
           Log.d("ViewModelVehicel","VehicleModel saved: ${checkVD}")
       }

        val retrieveVD = repository.retriveFromSingleVehicleDataOG()
        Log.d ("VehicleViewModel", "Retrieve USers are : ${retrieveVD}")
        val vehicleModel = VehicleModel("${BuildConfig.API_KEY_GRIEVANCE}",
                        "${fielId}", "${user_name}","${randomNumber}",
                     listOf(retrieveVD))

        val checkVehicleModel = repository.insertToSingleVehicle(vehicleModel)

        if(checkVehicleModel >-1) {
            _checkDataDB.value = "Success"
         //   repository.insertVehicleModelToServer(vehicleModel)
        }
    }


    val retrieveLiveVehicle = repository.retrieveVehicledataLive
    //save records to database
    fun insertToVehicles()  =  viewModelScope.launch{
        val randomNumber = (100..200000).random()
        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
        val currentDate = sdf.format(Date())
     val vehicleNo =   sharedPreference.getString("vehicle_number", "default")

    val vehicleData = VehiclesData("${_inputDistanceCovered.value}",
                         "${_inputHoursTravelled.value}",
                    "${randomNumber}", "${currentDate}",
        "${vehicleNo}")

        val checkVehicleEntry = repository.insertVehicle(vehicleData)
        if(checkVehicleEntry >-1) {
            _checkDataDB.value = "Success"
            repository.insertVehicleFieldData(vehicleData)
        }
    }
}


