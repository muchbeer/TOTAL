package raum.muchbeer.total.api

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import raum.muchbeer.total.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class DataRetroInstance {

    companion object {

        // val BASE_URL = "https://api.themoviedb.org/3/"

        val httpLogger = HttpLoggingInterceptor().apply {
            this.level = HttpLoggingInterceptor.Level.BODY }

        val client = OkHttpClient.Builder().apply {
            this.addInterceptor(httpLogger)
                .connectTimeout(30, TimeUnit.SECONDS) }.build()
    }

    fun dataInstance() : DataService {
        Log.i("RetrofitInstance", "The application has access the Mteja")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DataService::class.java)
    }

    fun LoginGrievanceInstance() : DataService {
        Log.i("LoginGrievance", "The application has access the Grievance")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_LOGIN_GRIEVANCE)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DataService::class.java)
    }

    fun PapListInstance() : DataService {
        Log.i("PapListGrievance", "The application has access the PapList")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_PAP_LIST)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DataService::class.java)
    }

    fun AgrievanceGeneral() : DataService {
        Log.i("Grievance", "The application has access the Grievance")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_PAP_ENTRY)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DataService::class.java)
    }
    fun HseData() : DataService {
        Log.i("Grievance", "The application has access the Grievance")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_HSE_ENTRY)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DataService::class.java)
    }

    fun EngagementData() : DataService {
        Log.i("Grievance", "The application has access the Grievance")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_ENGAGE_ENTRY)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DataService::class.java)
    }

    fun sendGrievanceGeneral() : DataService {
        Log.i("Grievance", "The application has access the Grievance")
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL_PAP_ENTRY)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(DataService::class.java)
    }

}