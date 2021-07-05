package raum.muchbeer.total

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import androidx.work.Configuration
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(), Configuration.Provider{

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override fun getWorkManagerConfiguration() = Configuration.Builder()
             .setWorkerFactory(workerFactory)
        .build()
}