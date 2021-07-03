package raum.muchbeer.total.di.db

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import raum.muchbeer.total.db.DataDB
import raum.muchbeer.total.db.engagedao.EngagementDao
import raum.muchbeer.total.db.grievancedao.*
import raum.muchbeer.total.db.hsedao.HseDao
import raum.muchbeer.total.db.vehicledao.VehicleDao
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class BenefitDBModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) : DataDB {
        return DataDB.getDatabaseInstance(context)
    }


    @Singleton
    @Provides
    fun providePapListDao(dataDB: DataDB) : PapListDao {
        return dataDB.PapListDao()
    }

    @Singleton
    @Provides
    fun provideagrievGeneral(dataBD: DataDB) : AgrievanceGeneralDao {
        return dataBD.AgrievanceDao()     }

    @Singleton
    @Provides
    fun provideBPapIdentity(dataBD: DataDB) : BpapDetailDao {
        return dataBD.BpapDetailDao()     }

    @Singleton
    @Provides
    fun provideCgrievEntries(dataBD: DataDB) : CgrievanceDao {
        return dataBD.CgrievanceDao()    }

    @Singleton
    @Provides
    fun provideDattachment(dataBD: DataDB) : DattachDao {
        return dataBD.DattachDao()      }

    @Singleton
    @Provides
    fun provideHse(dataBD: DataDB) : HseDao {
        return dataBD.HseDao()      }

    @Singleton
    @Provides
    fun provideEngagement(dataBD: DataDB) : EngagementDao {
        return dataBD.EngageDao()      }

    @Singleton
    @Provides
    fun provideVehicles(dataBD: DataDB) : VehicleDao {
        return dataBD.VehicleDao()      }
}