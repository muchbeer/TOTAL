package raum.muchbeer.total.repository.datasource

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel

interface DBPapUserSource {
    fun retrievePapListLive() : Flow<List<PapEntryListModel>>

    fun searchPapListSearch(searchName : String) : Flow<List<PapEntryListModel>>

    suspend fun insertSinglePap(data : PapEntryListModel) : Long

    suspend fun insertListPap(data: List<PapEntryListModel>)

    suspend fun deletepaps()
}