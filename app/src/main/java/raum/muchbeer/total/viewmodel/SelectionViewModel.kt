package raum.muchbeer.total.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.util.Base64.NO_WRAP
import android.util.Base64.encodeToString
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.grievance.BpapDetailModel
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.DattachmentModel
import raum.muchbeer.total.repository.Repository
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SelectionViewModel @Inject constructor(val repository: Repository,
                                             @ApplicationContext val context: Context
) : ViewModel() {

    private  var _displayJson2 = MutableLiveData<String>()
    val displayJson : LiveData<String>
        get() = _displayJson2

    private  var _checkLand = MutableLiveData<Boolean>()
    val checkLand : LiveData<Boolean>
        get() = _checkLand

    private val _user_selectLand2 = MutableLiveData<String>()
    private val _user_selectCrops2 = MutableLiveData<String>()
    private val _user_selectAnimal2 = MutableLiveData<String>()
    private val _user_selectHouse2 = MutableLiveData<String>()
    private val _user_selectGraves2 = MutableLiveData<String>()
    private var _compasation2 = MutableLiveData<String>()
    private var _photoUrl2 = MutableLiveData<String>()

    // The data source required by the Spinner Adapter
    var liveAgreeSigns = mutableListOf<String>()
    var liveSatisfyContract = mutableListOf<String>()
    var liveEntriesStatus = mutableListOf<String>()
    var liveRecommendation = mutableListOf<String>()

    private var _txtDisplyAgreeToSign2 = MutableLiveData<String>()
    val txtDisplayAgreeToSign : LiveData<String>
        get() = _txtDisplyAgreeToSign2

    private var _txtDisplaySatisfyContract2 = MutableLiveData<String>()
    val txtDisplaySatisfyContract : LiveData<String>
        get() = _txtDisplaySatisfyContract2

    private var _txtDisplayEntryStatus2 = MutableLiveData<String>()

    private var _txtDisplayRecommendation2 = MutableLiveData<String>()
    val txtDisplayRecommendation : LiveData<String>
        get() = _txtDisplayRecommendation2

    private var _txtPhotoComment2 = MutableLiveData<String>()

    private val _noAgreementInfo2 = MutableLiveData<String>()
    private val _noSatisfyContractInfo2 = MutableLiveData<String>()
    private val _noRecommendationInfo2 = MutableLiveData<String>()
    private val _compesationComment2 = MutableLiveData<String>()


    val gson = Gson()
    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
    var editor = sharedPreference.edit()

    val userSelectEntryStatus = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _txtDisplayEntryStatus2.value = get()
                editor.putString("entry_status", "${get()}")
                Log.d("SelectVM", "Select entry status is : ${get()}")
            }
        })
    }

    val userSelectRecommendation = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _txtDisplayRecommendation2.value = get()
                editor.putString("recommendation", "${get()}")
            }
        })
    }

    val userSelectionAgreeToSign = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _txtDisplyAgreeToSign2.value = get()
                editor.putString("agreeToSign", "${get()}")
            }
        })
    }

    val userSelectionSatisfyContract = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _txtDisplaySatisfyContract2.value = get()
                editor.putString("satisfyContract", "${get()}")
            }
        })
    }

    val observePhotoCommentEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _txtPhotoComment2.value = get()
                editor.putString("photoComment", "${get()}")

            }
        })
    }

    init {
        liveAgreeSigns = mutableListOf("   ","Yes", "No")
        liveSatisfyContract = mutableListOf("   ","Yes", "No")
        liveEntriesStatus = mutableListOf("    ", "Open", "In Progress", "Closes")
        liveRecommendation = mutableListOf("   ", "Yes", "No" )

        compasation()
    }



    fun saveToDbFinal() = viewModelScope.launch {
        Log.d("SelectViewModel", "Request to output data is clicked: ")
        Log.d("SelectViewModel", "the value check is : ${_checkLand.value}")
        val randomNumber = (100..200000).random()
        val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
        val currentDate = sdf.format(Date())


        val username =  sharedPreference.getString("username","default")
        val field_id = sharedPreference.getString("field_id","default")
        val valuation_id = sharedPreference.getString("valuation_id", "default")
        val full_name = sharedPreference.getString("full_name", "default")

        val cgrienvance = CgrievanceModel(
            "${_txtDisplyAgreeToSign2.value}",
            "${_noAgreementInfo2.value}",
            "${_txtDisplaySatisfyContract2.value}",
            "${_noSatisfyContractInfo2.value}",
            "${_txtDisplayRecommendation2.value}",
            "_${_noRecommendationInfo2.value}",
            "${_txtDisplayEntryStatus2.value}",
            "${currentDate}",
            "lsl",
            "lsls","${full_name}"
        )

        val dAttach = DattachmentModel("${_txtPhotoComment2.value}", "image","${_photoUrl2.value}", 0)

        val bpapDetails = BpapDetailModel("${valuation_id}", listOf(cgrienvance),
            listOf(dAttach))
        val agrievnceModel = AgrienceModel("${randomNumber}", "${BuildConfig.API_KEY_GRIEVANCE}",
            "${field_id}", "${username}", listOf(bpapDetails))

        val jsonFinal = gsonPretty.toJson(agrievnceModel)
        _displayJson2.value = jsonFinal
        Log.d("SelectViewModel", "The final list is : ${jsonFinal}")
    }

    val observeNoAgreementEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _noAgreementInfo2.value = get()
                editor.putString("noAgreement", "${get()}")
            }
        })
    }

    val observeNoSatisfyWithContractEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _noSatisfyContractInfo2.value = get()
                editor.putString("noSatisfyContract", "${get()}")
            }
        })
    }

    val observeCompasationCommentEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _compesationComment2.value = get()
                editor.putString("compasationComment", "${get()}")
            }
        })
    }

    val observeNoRecommendationEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _noRecommendationInfo2.value = get()
                editor.putString("noRecommendation", "${get()}")
            }
        })
    }

    //*************compute compasation

    fun checkLand(isLand : Boolean) {
        if (isLand)  {
           // _user_selectLand2.value = "Land"
            editor.putString("isLand", "  Land  ")
        }
    }

    fun checkCrops(isCrops : Boolean) {
        if (isCrops) {
          //  _user_selectCrops2.value = " Crops"
            editor.putString("isCrops", " Crops ")
        }

    }

    fun checkAnimal(isAnimal : Boolean) {
        if (isAnimal)  {
          //  _user_selectAnimal2.value = "Animal"
            editor.putString("isAnimal", " Animal ")
        }

    }

    fun checkHouse(isHouse: Boolean) {
        if (isHouse) {
            //_user_selectHouse2.value = "House"
            editor.putString("isHouse", " House ")
        }
    }

    fun checkGraves(isGrave : Boolean) {
        if (isGrave) {
          //  _user_selectGraves2.value = "Graves"
            editor.putString("isGrave", " Grave ")
        }

    }

    fun compasation()  = viewModelScope.launch{

        /*  _displayCompasation.value =  ( "${_user_selectLand2.value.toString()}" + "${_user_selectGraves2.value.toString()}"+
                 " ${_user_selectAnimal2.value.toString()}" + "${_user_selectHouse2.value.toString()}" + "${_user_selectCrops2.toString()}")
        */
        _compasation2.value = "Land"
        Log.d("SelectFragment", "After concatination become is : ${_user_selectAnimal2.value} for animal and ${_user_selectHouse2.value} for house ")
    }
    fun convertToBase64(bitmap: Bitmap)  = viewModelScope.launch{
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        val base64String = Base64.encodeToString(b, Base64.NO_WRAP)
        Log.d("PhotoFragment", "The base 64 is now become: ${base64String}")
        editor.putString("photoUrl", "${base64String}")
    }

}