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
import raum.muchbeer.total.repository.Repository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class EngageViewModel @Inject constructor( val repository: Repository,
                @ApplicationContext context: Context) : ViewModel() {

    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

    private var _checkEngageData = MutableLiveData<String>()
    val checkEngageData : LiveData<String>
        get() = _checkEngageData

    private var _inputWard = MutableLiveData<String>()
    val observeWard = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputWard.value = get()
            }
        })
    }

    private var _inputVillage = MutableLiveData<String>()
    val observeVillage = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputVillage.value = get()
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
        val randomNumber = (100..200000).random()
        val field_id = sharedPreference.getString("field_id", "default")
        val user_name = sharedPreference.getString("user_name", "default")
        val engageModel = EngageModel("${BuildConfig.API_KEY_GRIEVANCE}",
                "${field_id}", "${_inputKeypoint.value}",
                "${_inputListParticipant.value}", "${_inputMeetingTime.value}",
                "${_inputNoParticipant.value}", "${user_name}",
               "${_inputVillage.value}", "${_inputWard.value}", "${randomNumber}")

        val engageCheck = repository.insertToEngagement(engageModel)
        if (engageCheck >-1) {
            Log.d("EngageViewModel", "Database Engagement is successful entered")
            repository.insertEngagementToServer(engageModel)
            _checkEngageData.value = "Success"
        }
    }
}