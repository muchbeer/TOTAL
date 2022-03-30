package raum.muchbeer.total.db

import BpapDetailModel
import CgrievanceModel
import DattachmentModel
import android.content.Context
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import raum.muchbeer.total.db.engagedao.EngagementDao
import raum.muchbeer.total.db.grievancedao.*
import raum.muchbeer.total.db.hsedao.HseDao
import raum.muchbeer.total.db.vehicledao.VehicleDao
import raum.muchbeer.total.model.engagement.EngageModel
import raum.muchbeer.total.model.grievance.AgrienceModel
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel
import raum.muchbeer.total.model.hse.HseModel
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.model.vehicle.VehicleModel
import raum.muchbeer.total.model.vehicle.VehiclesData
import raum.muchbeer.total.model.vehicle.request.Vehicle


@Database(entities = [PapEntryListModel::class,
    AgrienceModel::class, BpapDetailModel::class, CgrievanceModel::class,
    DattachmentModel::class, Hsedata::class, EngageModel::class, VehiclesData::class,
    HseModel::class, Vehicle::class, VehicleModel::class]
    ,     version = 1, exportSchema = false,
    autoMigrations = [AutoMigration(from = 1, to = 2)])
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

        val MIGRATION_38_39 = object : Migration(38, 39) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE c_grievance_enter_values ADD COLUMN gender TEXT DEFAULT '' NOT NULL")
            }
        }
        fun getDatabaseInstance(context: Context) : DataDB
        {
            synchronized(this) {
                var instance = INSTANCE
                if(instance ==null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        DataDB::class.java,
                        "total_db")
                        .build()
                }
                return instance
            }

        }
    }
}