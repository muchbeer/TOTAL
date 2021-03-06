package raum.muchbeer.total.viewmodel

import android.content.Context
import android.util.Base64
import android.util.Base64OutputStream
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
import raum.muchbeer.total.model.ImageFirestore
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.DattachmentModel
import raum.muchbeer.total.repository.Repository
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
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


    private var _checkNulinput = MutableLiveData<Boolean>()
    val checkNulinput : LiveData<Boolean>
        get() = _checkNulinput

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

    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()

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
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputPhotoComment.value = get()
            }
        })
    }

   fun checkIfNullable()   {
       if (_inputGenderType.value !=null && _inputInquiryType.value!=null) {
           _checkNulinput.value = true } else   _checkNulinput.value = false

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
                "${_inputInquiryType.value}",
                "${_inputGenderType.value}"
        )
        editor.putString("reg_date", "${currentDate}")
        editor.apply()
        editor.commit()

        Log.d("Grievance", "Griev Object is : ${cgrienvance}")
     val checkGrive=   repository.insertToCgrienvance(cgrienvance)
        if (checkGrive > -1) Log.d("SampleVM", "Griev Database inserted") else
             Log.d("SampleVM", "Error insert into Grievance")

      /*  val bpapDetails = BpapDetailModel("${valuation_id}", listOf(cgrienvance),
            listOf(dAttach))
*/
    }

    fun attachMentDB() = viewModelScope.launch {
        val randomNumber = (100..200000).random()

        val dAttach = DattachmentModel("${_inputPhotoComment.value}", "image", "${_inputPhotoUrl.value}", "${randomNumber}")
        editor.putString("unique_data", "${randomNumber}")
        editor.apply()
        editor.commit()
        val checkAttachment =  repository.insertToDAttachment(dAttach)
        if (checkAttachment > -1) Log.d("SampleVM", "Attachment Database inserted") else
        Log.d("SampleVM", "Error Inserted into Attachment")

    }

    fun convertImageFileToBase64(imageFile: File): String {
        return ByteArrayOutputStream().use { outputStream ->
            Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
                imageFile.inputStream().use { inputStream ->
                    inputStream.copyTo(base64FilterStream)
                }
            }
            return@use outputStream.toString()
        }
    }

    fun convertFileImageToBase64(imageFile: File) =viewModelScope.launch {
        val sdf = SimpleDateFormat("dd-mm-yyyy:hh:mm:ss")
        val currentDate = sdf.format(Date())
        ByteArrayOutputStream().use { outputStream ->
            Base64OutputStream(outputStream, Base64.DEFAULT).use { base64FilterStream ->
                imageFile.inputStream().use { inputStream ->
                    inputStream.copyTo(base64FilterStream)
                }
            }
            Log.d("PhotoFragment", "The best one is base64 is : ${outputStream}")
            _inputPhotoUrl.value = imageFile.toString()
         val checkImage=   repository.insertingImages(ImageFirestore("${imageFile}",
             "gadiel",  "${currentDate}" ))
            Log.d("PhotoFragment", "ImageInserted record Number: ${checkImage}")
        }
    }

    fun convertFileImagefromJavaConvert(path: File) = viewModelScope.launch{
        val fout = FileOutputStream(path.toString() + ".txt")
        val fin = FileInputStream(path)

        System.out.println("File Size:" + path.length())

        val os = ByteArrayOutputStream()
        val base64out = Base64OutputStream(os, Base64.NO_WRAP)

        val buffer = ByteArray(3 * 512)
        var len = 0
        while (fin.read(buffer).also { len = it } >= 0) {
            base64out.write(buffer, 0, len)
        }

        println("Encoded Size:" + os.size())

        base64out.flush()
        base64out.close() //this did the tricks. Please see explanation.


        val result = String(os.toByteArray(), Charsets.UTF_8)

        fout.write(os.toByteArray())
        fout.flush()
        fout.close()
        os.close()
        fin.close()

        Log.d("FragmentPhoto", "Another byte64 from Java is : ${result}")
    }

}