package raum.muchbeer.total.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import raum.muchbeer.total.db.vehicledao.VehicleDao
import raum.muchbeer.total.model.grievance.BpapDetailModel
import raum.muchbeer.total.model.grievance.CgrievanceModel
import raum.muchbeer.total.model.grievance.DattachmentModel
import raum.muchbeer.total.model.hse.Hsedata
import raum.muchbeer.total.model.vehicle.VehiclesData
import java.lang.reflect.Type

class Converters {
    inline fun <reified T> Gson.fromJson(json: String) = fromJson<T>(
        json,
        object : TypeToken<T>() {}.type
    )

// ***********BGrievanceModel*****************

    @TypeConverter
    fun papDetailModelToJson(listOfpapIdentity: List<BpapDetailModel>): String {
        val gson = Gson()
        val gsonPretty = GsonBuilder().setPrettyPrinting()
            .excludeFieldsWithoutExposeAnnotation()
            .create()

        if (listOfpapIdentity.isNullOrEmpty()) return ""

        val papIDJson = gson.toJson(listOfpapIdentity)
        return papIDJson
    }

    @TypeConverter
    fun papDetailModelToPapModel(jsonPapDetail: String): List<BpapDetailModel> {
        if (jsonPapDetail.isNullOrEmpty()) return emptyList()

        val type: Type = object : TypeToken<List<BpapDetailModel?>?>() {}.type
        val paplistEntries: List<BpapDetailModel> =
            Gson().fromJson<List<BpapDetailModel>>(jsonPapDetail)
        return paplistEntries
    }

    // ***********CGrievanceModel*****************
    @TypeConverter
    fun papGrievanceDataToJson(listOfgrievance: List<CgrievanceModel>): String {
        val gson = Gson()

        if (listOfgrievance.isNullOrEmpty()) return ""

        val papIDJson = gson.toJson(listOfgrievance)
        return papIDJson
    }

    @TypeConverter
    fun papGrievanceDataToGModel(jsonGrievanceDetail: String): List<CgrievanceModel> {
        val gson = Gson()

        if (jsonGrievanceDetail.isNullOrEmpty()) return emptyList()

        val type: Type = object : TypeToken<List<CgrievanceModel?>?>() {}.type
        val paplistEntries: List<CgrievanceModel> =
            Gson().fromJson<List<CgrievanceModel>>(jsonGrievanceDetail)

        return paplistEntries
    }

    @TypeConverter
    fun hsedataToJson (hseData: List<Hsedata>) : String {
        val gson = Gson()
        if(hseData.isNullOrEmpty()) return ""
        val hseJson = gson.toJson(hseData)
        return hseJson
    }

 @TypeConverter
    fun hsedatafromJsonToModel(hseJson : String) : List<Hsedata>{
     val gson = Gson()

     if (hseJson.isNullOrEmpty()) return emptyList()

     val type: Type = object : TypeToken<List<Hsedata?>?>() {}.type
     val listHse: List<Hsedata> =
         Gson().fromJson<List<Hsedata>>(hseJson)

     return listHse
    }
    // ***********DAttachement*****************
    @TypeConverter
    fun papDAttachmentToJson(listOfAttachment: List<DattachmentModel>): String {
        val gson = Gson()

        if (listOfAttachment.isNullOrEmpty()) return ""

        val papAttachJson = gson.toJson(listOfAttachment)
        return papAttachJson
    }

    @TypeConverter
    fun papDAttachmentToGModel(jsonAttachment: String): List<DattachmentModel> {
        val gson = Gson()

        if (jsonAttachment.isNullOrEmpty()) return emptyList()

        val type: Type = object : TypeToken<List<CgrievanceModel?>?>() {}.type
        val listAttached: List<DattachmentModel> =
            Gson().fromJson<List<DattachmentModel>>(jsonAttachment)

        return listAttached
    }


    //******************************vehicles*******************88
    @TypeConverter
    fun vehiclesDataToJson(listOfVehicle: List<VehicleDao>): String {
        val gson = Gson()

        if (listOfVehicle.isNullOrEmpty()) return ""

        val papIDJson = gson.toJson(listOfVehicle)
        return papIDJson
    }

    @TypeConverter
    fun vehicleDataJsonToModel(jsonVehicleDetail: String): List<VehiclesData> {
        val gson = Gson()

        if (jsonVehicleDetail.isNullOrEmpty()) return emptyList()

        val type: Type = object : TypeToken<List<VehiclesData?>?>() {}.type
        val vehiclelistEntries: List<VehiclesData> =
            Gson().fromJson<List<VehiclesData>>(jsonVehicleDetail)

        return vehiclelistEntries
    }

}