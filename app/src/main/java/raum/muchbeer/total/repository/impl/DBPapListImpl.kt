package raum.muchbeer.total.repository.impl

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import raum.muchbeer.total.db.grievancedao.PapListDao
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel
import raum.muchbeer.total.repository.datasource.DBPapUserSource

class DBPapListImpl(val papListDao: PapListDao) : DBPapUserSource {
    override fun retrievePapListLive(): Flow<List<PapEntryListModel>> {
        return papListDao.retrievePapUsers()
    }

    override fun searchPapListSearch(searchName: String): Flow<List<PapEntryListModel>> {
        return papListDao.searchPaps(searchName)
    }

    override suspend fun insertSinglePap(data: PapEntryListModel): Long {
        return papListDao.insertSinglePap(data)
    }


    override suspend fun insertListPap(data: List<PapEntryListModel>) {
        return papListDao.insertPapList(data)
    }

    override suspend fun deletepaps() {
        papListDao.deleteAllFoods()
    }

}