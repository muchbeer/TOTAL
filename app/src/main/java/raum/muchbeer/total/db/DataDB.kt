package raum.muchbeer.total.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import raum.muchbeer.total.db.engagedao.EngagementDao
import raum.muchbeer.total.db.grievancedao.*
import raum.muchbeer.total.db.hsedao.HseDao
import raum.muchbeer.total.db.vehicledao.VehicleDao
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.grievance.BpapDetailModel
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.DattachmentModel
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.Vehicle


@TypeConverters(Converters::class)
@Database(entities = [PapEntryListModel::class,
    AgrienceModel::class, BpapDetailModel::class, CgrievanceModel::class,
    DattachmentModel::class, Hsedata::class, EngageModel::class, VehiclesData::class,
                     Vehicle::class]
    ,     version = 27, exportSchema = false)
abstract class DataDB : RoomDatabase() {
    abstract fun PapListDao() : PapListDao
    abstract fun AgrievanceDao() : AgrievanceGeneralDao
    abstract fun BpapDetailDao() : BpapDetailDao
    abstract fun CgrievanceDao() : CgrievanceDao
    abstract fun DattachDao() : DattachDao
    abstract fun HseDao() : HseDao
    abstract fun EngageDao() : EngagementDao
    abstract fun VehicleDao() : VehicleDao

    companion object {
        @Volatile
        private var INSTANCE : DataDB? = null

        fun getDatabaseInstance(context: Context) : DataDB
        {
            synchronized(this) {
                var instance = INSTANCE
                if(instance ==null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataDB::class.java,
                        "total_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }

        }
    }
}