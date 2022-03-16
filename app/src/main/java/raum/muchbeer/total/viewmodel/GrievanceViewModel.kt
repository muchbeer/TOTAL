package raum.muchbeer.total.viewmodel

import CgrievanceModel
import DattachmentModel
import android.content.Context
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
import raum.muchbeer.total.repository.Repository
import raum.muchbeer.total.utils.Constant
import raum.muchbeer.total.utils.Constant.Companion.DEFAULT_VALUE
import raum.muchbeer.total.utils.Constant.Companion.PREFERENCE_NAME
import raum.muchbeer.total.utils.Constant.Companion.VALUTION_NO_API
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject


@HiltViewModel
class GrievanceViewModel @Inject constructor(val repository: Repository,
                                             @ApplicationContext val context: Context
) :ViewModel() {

    private var _inputAgreeToSign = MutableLiveData<String>()
     val inputAgreeToSign : LiveData<String>
        get() = _inputAgreeToSign


    private var _displayJson = MutableLiveData<String>()
    val displayJson : LiveData<String>
        get() = _displayJson

    private var _checkLandSample = MutableLiveData<String>()
    private var _checkHouseSample = MutableLiveData<String>()
    private var _checkCropSample = MutableLiveData<String>()
    private var _checkGraveSample = MutableLiveData<String>()
    private var _checkAnimalSample = MutableLiveData<String>()

    private var _inputCompasationComment = MutableLiveData<String>()
    private var _noAgreementToSign = MutableLiveData<String>()
    private var _noSatisfyContract = MutableLiveData<String>()
    private var _inputRecommendation = MutableLiveData<String>()
    private var _combineGrievanceType = MutableLiveData<String>()

      val inputRecommendation : LiveData<String>
            get() = _inputRecommendation
    private var _inputSatisfyContract = MutableLiveData<String>()
       val inputSatisfy : LiveData<String>
                get() = _inputSatisfyContract

    private var _noRecommendation = MutableLiveData<String>()
    private var _inputEntryStatus = MutableLiveData<String>()
    val inputEntryStatus : LiveData<String>
        get() = _inputEntryStatus

    var liveAgreeSigns = mutableListOf<String>()
    var liveSatisfyContract = mutableListOf<String>()
    var liveEntriesStatus = mutableListOf<String>()
    var liveRecommendation = mutableListOf<String>()
    var liveInquireType = mutableListOf<String>()
    var liveGenderType = mutableListOf<String>()

    //Photo input
    var _inputPhotoComment = MutableLiveData<String>()
    var _inputPhotoUrl = MutableLiveData<String>()

    val gson = Gson()
    val gsonPretty = GsonBuilder().
    setPrettyPrinting().create()

    val sharedPreference =  context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()
    val valuation_id = sharedPreference.getString(VALUTION_NO_API, DEFAULT_VALUE)

    private var _inputInquiryType = MutableLiveData<String>()
    val inputInquiryType : LiveData<String>
        get() = _inputInquiryType

    private var _inputGenderType = MutableLiveData<String>()
       val inputGenderType : LiveData<String>
            get() = _inputGenderType

    init {

        liveAgreeSigns = mutableListOf("   ", "Yes", "No")
        liveSatisfyContract = mutableListOf("   ", "Yes", "No")
        liveEntriesStatus = mutableListOf("    ", "Open", "In Progress", "Closes")
        liveRecommendation = mutableListOf("   ", "Yes", "No")
        liveInquireType = mutableListOf("", "Grievance", "Issue", "Query", "Concern")
        liveGenderType = mutableListOf("", "Male", "Female")

    }


    val userSelectInquiryType = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputInquiryType.value = get()
            }
        })
    }


    val userSelectGenderType = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputGenderType.value = get()
            }
        })
    }

    val userSelectionAgreeToSign = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputAgreeToSign.value = get()
            }
        })
    }

    val userSelectEntryStatus = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.d(TAG, "Select entry status is : ${get()}")
                _inputEntryStatus.value = get()
            }
        })
    }

    val observeNoSatisfyWithContractEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _noSatisfyContract.value = get()
            }
        })
    }

    val userSelectRecommendation = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputRecommendation.value = get()
            }
        })
    }

    val userSelectionSatisfyContract = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputSatisfyContract.value = get()
            }
        })
    }

    val observeCompasationCommentEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputCompasationComment.value = get()
            }
        })
    }

    val observeNoAgreementEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _noAgreementToSign.value = get()
                Log.d(TAG, "selected no agreement is : ${_noAgreementToSign.value}")
            }
        })
    }

    val observeNoRecommendationEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _noRecommendation.value = get()
            }
        })
    }

    //Photo Observation
    val observePhotoCommentEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputPhotoComment.value = get()
            }
        })
    }

    fun checkLand(isLand: Boolean) {
        if (isLand) _checkLandSample.value = "Land"
        else _checkLandSample.value="None"     }

    fun checkCrops(isCrops: Boolean) {
        if (isCrops) _checkCropSample.value = " Crops"
        else _checkCropSample.value="None"     }

    fun checkAnimal(isAnimal: Boolean) {
        if (isAnimal) _checkAnimalSample.value = "Animal"
        else _checkAnimalSample.value="None"     }

    fun checkHouse(isHouses: Boolean) {
        if (isHouses) _checkHouseSample.value = "House"
        else _checkHouseSample.value="None"     }

    fun checkGraves(isGrave: Boolean) {
        if (isGrave) _checkGraveSample.value = "Graves"
        else _checkGraveSample.value="None"     }


    fun grievenceDB() = viewModelScope.launch{
        Log.d(TAG, "agreeToSign obtained is : ${_inputAgreeToSign.value}")
        Log.d(TAG, " inputCompesation is : ${_inputCompasationComment.value}")
        Log.d(TAG, "CheckAnimal obtained : ${_checkAnimalSample.value}")
        Log.d(TAG, "CheckCrop obtained is : ${_checkCropSample.value}")
        Log.d(TAG, " No agreement obtained : ${_noAgreementToSign.value}")
        Log.d(TAG, " Recommendation obtained : ${_noRecommendation.value}")
        Log.d(TAG, " EntryStatus obtained : ${_inputEntryStatus.value}")
        Log.d(TAG, " AgreeTo Sign obtained : ${_inputAgreeToSign.value}")

        _combineGrievanceType.value = "${_checkAnimalSample.value}"+ "  ${_checkGraveSample.value}" +
                "  ${_checkHouseSample.value}" + "  ${_checkLandSample.value}" + "  ${_checkCropSample.value}"
        Log.d(TAG, "Value obtained is Type is Sign: ${_combineGrievanceType.value}")

        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
        val currentDate = sdf.format(Date())

        val full_name = sharedPreference.getString(Constant.FULL_NAME, "default")

        val cgrienvance = CgrievanceModel(
                agreetosign = "${_inputAgreeToSign.value}",
                notagreetosign = "${_noAgreementToSign.value}",
                satisfiedwithcontract = "${_inputSatisfyContract.value}",
                notsatisfiedwithcontract = "${_noSatisfyContract.value}",
                anyrecomendations = "${_inputRecommendation.value}",
                recomendations = "_${_noRecommendation.value}",
                gstatus = "${_inputEntryStatus.value}",
                reg_date = currentDate,
                grievancetype = "${_combineGrievanceType.value}",
                grievanceexplanation = "${_inputCompasationComment.value}",
                full_name = full_name!!,
                inquirytype = "${_inputInquiryType.value}",
                gender =  "${_inputGenderType.value}",
                valuation_no = valuation_id!!
        )
        editor.putString("reg_date", currentDate)
        editor.apply()
        editor.commit()

        Log.d(TAG, "Griev Object is : $cgrienvance")
        val checkGrive=   repository.insertToCgrienvance(cgrienvance)

        if (checkGrive > -1) Log.d("SampleVM", "Griev Database inserted") else
             Log.d(TAG, "Error insert into Grievance")



    }

    fun attachMentDB() = viewModelScope.launch {
        val randomNumber = (100..200000).random()

        val dAttach = DattachmentModel(
            category_name = "${_inputPhotoComment.value}",
            file_name ="Input Description",
            file_url = "${_inputPhotoUrl.value}",
            isUploaded=false,
            valuation_number = valuation_id!!)
        editor.putString(Constant.UNIQUE_DATA, "${randomNumber}")
        editor.apply()
        editor.commit()
        val checkAttachment =  repository.insertToDAttachment(dAttach)
        if (checkAttachment > -1) Log.d(TAG, "Attachment Database inserted") else
        Log.d(TAG, "Error Inserted into Attachment")

    }

companion object {
    private val TAG = GrievanceViewModel::class.simpleName  }
}