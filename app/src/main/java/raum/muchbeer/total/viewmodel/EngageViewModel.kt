package raum.muchbeer.total.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.vehicle.request.Vehicle
import raum.muchbeer.total.repository.Repository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EngageViewModel @Inject constructor( val repository: Repository,
                @ApplicationContext context: Context) : ViewModel() {

    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)


    private val _navigateToFormFilling = MutableLiveData<EngageModel>()
    val navigateToFormFilling: LiveData<EngageModel>
        get() = _navigateToFormFilling


    private var _checkEngageData = MutableLiveData<String>()
    val checkEngageData : LiveData<String>
        get() = _checkEngageData

    var liveAreaLevel = mutableListOf<String>()

    init {
        liveAreaLevel = mutableListOf("   ","Regional", "District", "Ward", "Village")
    }

    val engageLiveData = repository.retrieveEngageLive

    private var _selectAreaLocation = MutableLiveData<String>()
    val userSelectArea = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _selectAreaLocation.value = get()
            }
        })
    }

    private var _inputMeetingTime = MutableLiveData<String>()
    val observeMeetingTime = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputMeetingTime.value = get()
            }
        })
    }

    private var _inputNoParticipant = MutableLiveData<String>()
    val observeNoParticipant = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputNoParticipant.value = get()
            }
        })
    }

    private var _inputListParticipant = MutableLiveData<String>()
    val observeListParticipant = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputListParticipant.value = get()
            }
        })
    }

    private var _inputKeypoint = MutableLiveData<String>()
    val observeKeypoint = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputKeypoint.value = get()
            }
        })
    }

    fun insertToEngagement()  =  viewModelScope.launch{
        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
        val currentDate = sdf.format(Date())

        val randomNumber = (100..200000).random()
        val field_id = sharedPreference.getString("field_id", "default")
        val user_name = sharedPreference.getString("username", "default")
        val engageModel = EngageModel("${BuildConfig.API_KEY_GRIEVANCE}",
                "${field_id}", "${_inputKeypoint.value}",
                "${_inputListParticipant.value}", "${_inputMeetingTime.value}",
                "${_inputNoParticipant.value}", "${user_name}",
               "${_selectAreaLocation.value}",  "${currentDate}"
        )

        val engageCheck = repository.insertToEngagement(engageModel)
        if (engageCheck >-1) {
            Log.d("EngageViewModel", "Database Engagement is successful entered")
          //  repository.insertEngagementToServer(engageModel)
            _checkEngageData.value = "Success"
        }
    }

    fun displayComplete() {    _navigateToFormFilling.value = null    }

    fun displayFormFilling(engageList: EngageModel) {  _navigateToFormFilling.value = engageList   }
}