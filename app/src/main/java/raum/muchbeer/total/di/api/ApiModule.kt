package raum.muchbeer.total.di.api

import androidx.navigation.Navigator
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import raum.muchbeer.total.BuildConfig
import raum.muchbeer.total.api.DataRetroInstance
import raum.muchbeer.total.api.DataService
import raum.muchbeer.total.repository.datasource.NetDataSource
import raum.muchbeer.total.repository.impl.NetDataSourceImpl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun providesGSONBuilder(): Gson {
        return GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()
    }

    @Singleton
    @Provides
    fun provideRetrofitBuilder(gson : Gson) : Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


/*    @Singleton
    @Provides
    fun provideMovieService(retrofitBuilder : Retrofit.Builder) : DataService {
        return retrofitBuilder.build().create(DataService::class.java)
    }*/


    @Singleton
    @Provides
    fun provideDataCollectionService() : DataService {
        return DataRetroInstance().dataInstance()
    }

    @Singleton
    @Provides
    fun provideNetworkDataSource(dataService: DataService) : NetDataSource {
        return NetDataSourceImpl(dataService)     }

    @Named("agrievancegeneral")
    @Singleton
    @Provides
    fun provideGrievanceRecord() : DataService {
        return DataRetroInstance().AgrievanceGeneral()
    }

    @Named("grievance")
    @Singleton
    @Provides
    fun providesLoginGrievanceService() : DataService {
        return DataRetroInstance().LoginGrievanceInstance()     }

    @Named("paplist")
    @Singleton
    @Provides
    fun providesPapListService()  : DataService {
        return DataRetroInstance().PapListInstance()     }

    @Named("sendGriev")
    @Singleton
    @Provides
    fun providesSendPapListService()  : DataService {
        return DataRetroInstance().PapListInstance()     }

    @Named("hse")
    @Singleton
    @Provides
    fun providesHse()  : DataService {
        return DataRetroInstance().HseData()     }

    @Named("engage")
    @Singleton
    @Provides
    fun providesEngagement()  : DataService {
        return DataRetroInstance().EngagementData()     }

    @Named("gravitee")
    @Singleton
    @Provides
    fun providesGrievenceGeneral()  : DataService {
        return DataRetroInstance().sendGrievanceGeneral()     }
}