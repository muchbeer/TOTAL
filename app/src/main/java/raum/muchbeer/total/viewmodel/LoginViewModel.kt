package raum.muchbeer.total.viewmodel

import android.content.Context
import android.util.Log
import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.model.DataState
import raum.muchbeer.total.model.grievance.papform.PapEntity
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel
import raum.muchbeer.total.model.users.GrievanceCredentialEntity
import raum.muchbeer.total.model.users.UserModel
import raum.muchbeer.total.repository.PapListStateEvent
import raum.muchbeer.total.repository.Repository
import raum.muchbeer.total.utils.Constant
import raum.muchbeer.total.utils.Constant.Companion.PREFERENCE_NAME
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(val repository: Repository,
                    @ApplicationContext context: Context) : ViewModel(){

    val sharedPreference =  context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
    val editor = sharedPreference.edit()

    private val _loginState = MutableStateFlow<DataState<UserModel>>(DataState.Loading)
    val loginState : StateFlow<DataState<UserModel>>
        get() = _loginState

/*    private val _papListState : MutableLiveData<PapListStateEvent<PapEntryListModel>> = MutableLiveData()
    val papListState : LiveData<PapListStateEvent<PapEntryListModel>>
        get() = _papListState*/

    private val _navigateToFormFilling = MutableSharedFlow<PapEntryListModel>()
    val navigateToFormFilling: SharedFlow<PapEntryListModel>
        get() = _navigateToFormFilling

    private val _fieldIDReturn = MutableLiveData<String>()

    private val _read_Status_Code = MutableStateFlow("")
       val read_status_Code : StateFlow<String>
            get()= _read_Status_Code


    private val _inputUserName = MutableLiveData<String>()
    private val _inputPassWord = MutableLiveData<String>()
    private val _hideKeyboardvalue = MutableLiveData<String>()
    val hideKeyboardValue : LiveData<String>
        get() = _hideKeyboardvalue

    val gsonPretty = GsonBuilder().setPrettyPrinting().create()

    val observeUserNameEdtTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputUserName.value = get()
            }
        })
    }

    val observePasswordEditTxt = ObservableField<String>().apply {
        addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                _inputPassWord.value = get()
            }

        })
    }


    //****************LOGIN USER
    fun loginUser() = viewModelScope.launch {

        _hideKeyboardvalue.value = "hideKeyboard"

        val authGrievenceModel = GrievanceCredentialEntity(
            BuildConfig.API_KEY,
            _inputUserName.value.toString(), _inputPassWord.value.toString()
        )

        repository.executeLogin(authGrievenceModel).collect {
            _loginState.value = it
        }
        editor.putString("user_id","${_inputUserName.value}")
        editor.apply()
        editor.commit()
    }


    //****************DISPLAY PAPS
    val receiveListOfPaps = repository.getPapListFlow()

    //*************************Search Engine****************************
    private val _searchItemState = MutableStateFlow("")

    fun setSearchQuery(search: String) {   _searchItemState.value = search    }


    @ExperimentalCoroutinesApi
    @FlowPreview
    val searchPapsListFlow : Flow<List<PapEntryListModel>> = _searchItemState
        .debounce(300)
        .distinctUntilChanged()
        .flatMapLatest { search ->

            repository.searchPaps(search)
        } .catch { throwable ->
            Log.d("SampeViewModel", "The error searched : ${throwable.message}")
            //   Timber.d("The error occured is : ${throwable.message}")
        }

    fun displayFormFilling(papList: PapEntryListModel)  = viewModelScope.launch {
        _navigateToFormFilling.emit(papList) }



        //******utility
    fun keyboardComplete() {  _hideKeyboardvalue.value=""   }
    
    fun retrieveFromFirestore() = viewModelScope.launch{
        repository.retrieveFromFireStoreData()
    }
}
