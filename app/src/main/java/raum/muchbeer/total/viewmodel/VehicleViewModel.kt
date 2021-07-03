package raum.muchbeer.total.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import raum.muchbeer.total.repository.Repository
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor (val repository: Repository,
                                            @ApplicationContext context: Context) : ViewModel(){


}