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
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.repository.Repository
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HseViewModel @Inject constructor(val repository: Repository,
                                       @ApplicationContext context: Context)
    : ViewModel(){

    private var _checkhseData = MutableLiveData<String>()
        val checkHseData : LiveData<String>
            get() = _checkhseData

    var liveToolboxs = mutableListOf<String>()
    var liveifAnyIncidenceOccur = mutableListOf<String>()
    var liveifSecurityOccur = mutableListOf<String>()

    private val _selectToolbox = MutableLiveData<String>()
    private val _selectIncidence = MutableLiveData<String>()
      val selectIncidence : LiveData<String>
            get() = _selectIncidence

    private val _selectSecurity = MutableLiveData<String>()
        val selectSecurities : LiveData<String>
            get() = _selectSecurity


    init {

        liveToolboxs = mutableListOf("   ","Safety", "Healthy")
        liveifAnyIncidenceOccur = mutableListOf("   ","Yes", "No")
        liveifSecurityOccur = mutableListOf("   ", "Yes", "No")
    }

    val userSelectSecurity = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _selectSecurity.value = get()
            }
        })
    }

    val userSelectIncidence = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _selectIncidence.value = get()
            }
        })
    }

    val userSelectToolbox = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _selectToolbox.value = get()
            }
        })
    }

    private var _inputInspection = MutableLiveData<String>()
    val observeInspectionReport = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputInspection.value = get()
            }
        })
    }

    private var _inputCommentOnToolbox = MutableLiveData<String>()
    val observeCommentOnToolbox = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputCommentOnToolbox.value = get()
            }
        })
    }

    private var _inputAccomodation = MutableLiveData<String>()
    val observeAccomodation = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputAccomodation.value = get()
            }
        })
    }

    private var _inputCommentForIncidence = MutableLiveData<String>()
    val observeCommentForIncidence = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputCommentForIncidence.value = get()
            }
        })
    }

    private var _inputCommentForSecurity = MutableLiveData<String>()
    val observeCommentForSecurity = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputCommentForSecurity.value = get()
            }
        })
    }

    private var _inputObservation = MutableLiveData<String>()
    val observeObservation = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputObservation.value = get()
            }
        })
    }


    fun insertToHSE()  =  viewModelScope.launch{
        val randomNumber = (100..200000).random()
        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
        val currentDate = sdf.format(Date())

        val hseData = Hsedata("${_inputAccomodation.value}", "${_inputObservation.value}",
        "${_selectSecurity.value}", "${_inputInspection.value}",
            "${_selectIncidence.value}", "${_inputCommentForIncidence.value}",
        "${randomNumber}", "${currentDate}", "${_inputCommentForSecurity.value}",
        "${_selectToolbox.value}", "${_inputCommentOnToolbox.value}")

        val checkHseData = repository.insertToHse(hseData)
        if (checkHseData > -1) {
            Log.d("HSeViewModel", "Successful entered to database HSE")
            _checkhseData.value = "Success"

            repository.insertHseToServer(hseData)
        }
    }    }
