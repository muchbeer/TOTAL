package raum.muchbeer.total.di.db

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import raum.muchbeer.total.db.engagedao.EngagementDao
import raum.muchbeer.total.db.grievancedao.*
import raum.muchbeer.total.db.hsedao.HseDao
import raum.muchbeer.total.db.vehicledao.VehicleDao
import raum.muchbeer.total.repository.Repository
import raum.muchbeer.total.repository.datasource.DBGrievanceSource
import raum.muchbeer.total.repository.datasource.DBPapUserSource
import raum.muchbeer.total.repository.impl.DBGrievanceImpl
import raum.muchbeer.total.repository.impl.DBPapListImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBDataSourceModule {

    @Singleton
    @Provides
    fun providePapListModule(papListDao: PapListDao) : DBPapUserSource {
        return DBPapListImpl(papListDao)
    }

    @Singleton
    @Provides
    fun provideGrievanceModule(grievDao: AgrievanceGeneralDao, bGrievDetailDao: BpapDetailDao,
                               cGrievDao: CgrievanceDao, dAttachDao : DattachDao,
                               hseDao : HseDao, engageDao: EngagementDao, vehicleDao: VehicleDao
    ) : DBGrievanceSource {
        return DBGrievanceImpl(grievDao, bGrievDetailDao, cGrievDao, dAttachDao,
            hseDao, engageDao, vehicleDao)    }


}