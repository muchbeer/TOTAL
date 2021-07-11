package raum.muchbeer.total.viewmodel

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
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.grievance.BpapDetailModel
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel
import raum.muchbeer.total.repository.Repository
import java.text.SimpleDateFormat
import java.util.*
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

    private val _checkOnDoneStatus = MutableLiveData<String>()
    val checkOnDoneStatus: LiveData<String>
        get() = _checkOnDoneStatus


    private val _navigateToRecordDetailFragment = MutableLiveData<CgrievanceModel>()
    val navigateToRecordDetail: LiveData<CgrievanceModel>
        get() = _navigateToRecordDetailFragment

    init {
        _checkOnDoneStatus.value = ""
    }

    fun displayFormFilling(grievance: CgrievanceModel) {  _navigateToRecordDetailFragment.value = grievance   }

    fun displayComplete() {    _navigateToRecordDetailFragment.value = null    }

    fun viewInformation() = viewModelScope.launch {
      val reg_date =  sharedPreference.getString("reg_date", "default")
        val unique_data = sharedPreference.getString("unique_data", "default")
      val attachment =  repository.retrieveAttachment(unique_data!!)
        val cGrievance = repository.retrieveCgrievance(reg_date!!)
          val jsonCGrev = gsonPretty.toJson(cGrievance)
        val jsonAttach = gsonPretty.toJson(attachment)
        Log.d("FinalViewModel", "Values of cGrievance is : ${jsonCGrev}")
        Log.d("FinalViewModel", "Values of attachment is : ${jsonAttach}")

        val valuation_id = sharedPreference.getString("valuation_id", "default")
        val randomNumber = (100..200000).random()
        val username =  sharedPreference.getString("username","default")
        val field_id = sharedPreference.getString("field_id","default")

        val bpapDetails = BpapDetailModel("${valuation_id}", listOf(cGrievance),
            listOf(attachment))

        val agrievnceModel = AgrienceModel("${randomNumber}", "${BuildConfig.API_KEY_GRIEVANCE}",
            "${field_id}", "${username}", listOf(bpapDetails))

        val jsonFinal = gsonPretty.toJson(agrievnceModel)
        Log.d("FinalViewModel", "Values of Grievance is  : ${jsonFinal}")
     val mainTable =   repository.insertToAGriev(agrievnceModel)
        if (mainTable > -1) Log.d("FinalViewModel", "Main Table data inserted number: ${mainTable} ") else {
            Log.d("FinalViewModel", "Main Table data did not inserted")
        }
    }

    val viewdataRecorded = repository.cgrievanceLiveData
    fun clearDatabase() = viewModelScope.launch {
        val retrieveData = repository.retrieveAGrieveGeralList()
        retrieveData.forEach {
         if (!it.field_id.isNullOrEmpty()) {
             _checkOnDoneStatus.value = "Success"
           //  repository.insertGrievanceToserver(it)
         }
            val listG = it.papdetails
            listG.forEach {
                Log.d("FinalViewMode", "BLevel is : ${it.valuation_number}")
            }
            Log.d("FinalViewModel", "Main is : ${it.field_id}")
            Log.d("FinalViewModel", "Grieve is ")
        }
    }

}