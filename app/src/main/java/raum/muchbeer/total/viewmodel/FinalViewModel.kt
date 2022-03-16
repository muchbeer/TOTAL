package raum.muchbeer.total.viewmodel

import BpapDetailModel
import CgrievanceModel
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.repository.Repository
import raum.muchbeer.total.utils.Constant.Companion.DEFAULT_VALUE
import raum.muchbeer.total.utils.Constant.Companion.FIELD_ID_API
import raum.muchbeer.total.utils.Constant.Companion.REG_DATE
import raum.muchbeer.total.utils.Constant.Companion.UNIQUE_DATA
import raum.muchbeer.total.utils.Constant.Companion.USER_NAME_API
import raum.muchbeer.total.utils.Constant.Companion.VALUTION_NO_API
import javax.inject.Inject

@HiltViewModel
class FinalViewModel @Inject constructor(val repository: Repository,
                                         @ApplicationContext val context: Context) : ViewModel() {

    val sharedPreference =  context.getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

    val gson = Gson()
    val gsonPretty = GsonBuilder().
    setPrettyPrinting().create()


    private  var _displayJson = MutableLiveData<String>()
    val displayJson : LiveData<String>
        get() = _displayJson

    private val _checkOnDoneStatus = MutableStateFlow<String>("")
    val checkOnDoneStatus : StateFlow<String> = _checkOnDoneStatus.asStateFlow()
   /* private val _checkOnDoneStatus = MutableLiveData<String>()
    val checkOnDoneStatus: LiveData<String>
        get() = _checkOnDoneStatus
*/

    private val _navigateToRecordDetailFragment = MutableSharedFlow<CgrievanceModel>()
    val navigateToRecordDetail: SharedFlow<CgrievanceModel> =
        _navigateToRecordDetailFragment.asSharedFlow()
            /*  private val _navigateToRecordDetailFragment = MutableLiveData<CgrievanceModel>()
              val navigateToRecordDetail: LiveData<CgrievanceModel>
                  get() = _navigateToRecordDetailFragment*/

    init {

     //   _checkOnDoneStatus.emit("")
    }

    fun displayFormFilling(grievance: CgrievanceModel) = viewModelScope.launch {
        _navigateToRecordDetailFragment.emit(grievance)    }

   // fun displayComplete() {    _navigateToRecordDetailFragment.value = null    }

    fun viewInformation() = viewModelScope.launch {


        val attachment =  "repository.retrievretrieveAttachment(unique_data!!)"
        val cGrievance = "repository.retrieveCgrievance(reg_date!!)"


        val jsonCGrev = gsonPretty.toJson(cGrievance)
        val jsonAttach = gsonPretty.toJson(attachment)

        Log.d("FinalViewModel", "Values of cGrievance is : $jsonCGrev")

        val valuation_id = sharedPreference.getString(VALUTION_NO_API, DEFAULT_VALUE )
        val randomNumber = (100..200000).random()
        val username =  sharedPreference.getString(USER_NAME_API,DEFAULT_VALUE)
        val field_id = sharedPreference.getString(FIELD_ID_API,DEFAULT_VALUE)



        val bpapDetails = BpapDetailModel(
            valuation_number =  "${valuation_id}",
            a_grievance_id = "",
            user_name = "",
            grievance = listOf(),
            attachments = listOf()
           )

        val agrievnceModel = AgrienceModel(
            primary_key = randomNumber.toString(),
            apiKey = BuildConfig.API_KEY_GRIEVANCE,
            field_id = field_id!!,
            user_name = username!!,
            papdetails = listOf(bpapDetails)
        )

        val jsonFinal = gsonPretty.toJson(agrievnceModel)
        Log.d("FinalViewModel", "Values of Grievance is  : ${jsonFinal}")

        val mainTable =   repository.insertToAGriev(agrievnceModel)
        if (mainTable > -1) Log.d("FinalViewModel", "Main Table data inserted number: ${mainTable} ") else {
          Log.d("FinalViewModel", "Main Table data did not inserted")
        }
    }



}