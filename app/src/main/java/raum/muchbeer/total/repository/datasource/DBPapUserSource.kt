package raum.muchbeer.total.repository.datasource

import androidx.lifecycle.LiveData
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel

interface DBPapUserSource {
    fun retrievePapListLive() : LiveData<List<PapEntryListModel>>

    suspend fun insertSinglePap(data : PapEntryListModel) : Long

    suspend fun insertListPap(data: List<PapEntryListModel>)

    //*********************PAPS**********************
    fun searchPaps(fullName: String) : LiveData<List<PapEntryListModel>>
}