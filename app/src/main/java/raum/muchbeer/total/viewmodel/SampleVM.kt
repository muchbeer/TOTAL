package raum.muchbeer.total.viewmodel

import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
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
class SampleVM @Inject constructor(val repository: Repository,
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

    var liveAgreeSigns = mutableListOf<String>()
    var liveSatisfyContract = mutableListOf<String>()
    var liveEntriesStatus = mutableListOf<String>()
    var liveRecommendation = mutableListOf<String>()
    var liveInquireType = mutableListOf<String>()

    //Photo input
    var _inputPhotoComment = MutableLiveData<String>()
    var _inputPhotoUrl = MutableLiveData<String>()

    val gson = Gson()
    val gsonPretty = GsonBuilder().
    setPrettyPrinting().create()

    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

    init {

        liveAgreeSigns = mutableListOf("   ","Yes", "No")
        liveSatisfyContract = mutableListOf("   ","Yes", "No")
        liveEntriesStatus = mutableListOf("    ", "Open", "In Progress", "Closes")
        liveRecommendation = mutableListOf("   ", "Yes", "No" )
        liveInquireType = mutableListOf("   ", "Grievance", "Issue", "Query", "Concern")
    }

        private var _inputInquiryType = MutableLiveData<String>()
    val userSelectInquiryType = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputInquiryType.value = get()
            }
        })
    }

    val userSelectionAgreeToSign = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputAgreeToSign.value = get()
            }
        })
    }

    val userSelectEntryStatus = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                Log.d("SelectVM", "Select entry status is : ${get()}")
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
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputRecommendation.value = get()
            }
        })
    }

    val userSelectionSatisfyContract = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
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
                Log.d("SampleVM", "selected no agreement is : ${_noAgreementToSign.value}")
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
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputPhotoComment.value = get()
            }
        })
    }

    fun checkLand(isLand : Boolean) {
        if (isLand) _checkLandSample.value = "Land"
        else _checkLandSample.value="None"     }

    fun checkCrops(isCrops : Boolean) {
        if (isCrops) _checkCropSample.value = " Crops"
        else _checkCropSample.value="None"     }

    fun checkAnimal(isAnimal : Boolean) {
        if (isAnimal) _checkAnimalSample.value = "Animal"
        else _checkAnimalSample.value="None"     }

    fun checkHouse(isHouses: Boolean) {
        if (isHouses) _checkHouseSample.value = "House"
        else _checkHouseSample.value="None"     }

    fun checkGraves(isGrave : Boolean) {
        if (isGrave) _checkGraveSample.value = "Graves"
        else _checkGraveSample.value="None"     }


    fun grievenceDB() = viewModelScope.launch{
            Log.d("SampleVM", "Value obtained is : ${_inputAgreeToSign.value}")
        Log.d("SampleVM", "Value obtained inputCompesation is : ${_inputCompasationComment.value}")
        Log.d("SampleVM", "Value obtained is : ${_inputAgreeToSign.value}")
        Log.d("SampleVM", "Value obtained Animal : ${_checkAnimalSample.value}")
        Log.d("SampleVM", "Value obtained is Crop : ${_checkCropSample.value}")
        Log.d("SampleVM", "Value obtained is No agreement : ${_noAgreementToSign.value}")
        Log.d("SampleVM", "Value obtained is Recommendation: ${_noRecommendation.value}")
        Log.d("SampleVM", "Value obtained is EntryStatus: ${_inputEntryStatus.value}")
        Log.d("SampleVM", "Value obtained is AgreeTo Sign: ${_inputAgreeToSign.value}")
        _combineGrievanceType.value = "${_checkAnimalSample.value}"+ "  ${_checkGraveSample.value}" +
                "  ${_checkHouseSample.value}" + "  ${_checkLandSample.value}" + "  ${_checkCropSample.value}"
        Log.d("SampleVM", "Value obtained is Type is Sign: ${_combineGrievanceType.value}")


        val randomNumber = (100..200000).random()
        val sdf = SimpleDateFormat("yyyy-M-dd hh:mm:ss")
        val currentDate = sdf.format(Date())

        val full_name = sharedPreference.getString("full_name", "default")

        val cgrienvance = CgrievanceModel(
            "${_inputAgreeToSign.value}",
            "${_noAgreementToSign.value}",
            "${_inputSatisfyContract.value}",
            "${_noSatisfyContract.value}",
            "${_inputRecommendation.value}",
            "_${_noRecommendation.value}",
            "${_inputEntryStatus.value}",
            "${currentDate}",
            "${_combineGrievanceType.value}",
            "${_inputCompasationComment.value}",
            "${full_name}",
            "${_inputInquiryType.value}"
        )

        Log.d("Grievance", "Griev Object is : ${cgrienvance}")
     val checkGrive=   repository.insertToCgrienvance(cgrienvance)
        if (checkGrive > -1) Log.d("SampleVM", "Griev Database inserted") else
             Log.d("SampleVM", "Error insert into Grievance")

      /*  val bpapDetails = BpapDetailModel("${valuation_id}", listOf(cgrienvance),
            listOf(dAttach))
*/
    }

    fun attachMentDB() = viewModelScope.launch {
        val dAttach = DattachmentModel("${_inputPhotoComment.value}", "image","${_inputPhotoUrl.value}", 0)
        val checkAttachment =  repository.insertToDAttachment(dAttach)
        if (checkAttachment > -1) Log.d("SampleVM", "Attachment Database inserted") else
        Log.d("SampleVM", "Error Inserted into Attachment")

    }
    fun convertToBase64(bitmap: Bitmap)  = viewModelScope.launch{
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val b = baos.toByteArray()
        val base64String = Base64.encodeToString(b, Base64.NO_WRAP)
        Log.d("PhotoFragment", "The base 64 is now become: ${base64String}")
        _inputPhotoUrl.value = base64String
    }
}