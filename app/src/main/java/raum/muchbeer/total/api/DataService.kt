package raum.muchbeer.total.api

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface DataService {


    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )    @POST(".")
    suspend fun sendData(@Body requestData: RequestBody) : Response<ResponseBody>

    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )    @POST(".")
    suspend fun sendLoginGrievance(@Body requestData: RequestBody) : Response<ResponseBody>

    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )    @POST(".")
    suspend fun sendGrievanceDataToServer(@Body requestData: RequestBody) : Response<ResponseBody>

    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )    @POST(".")
    suspend fun sendHseDataToServer(@Body requestData: RequestBody) : Response<ResponseBody>

    @Headers(
        "Accept: application/json",
        "Content-type:application/json"
    )    @POST(".")
    suspend fun sendEngageDataToServer(@Body requestData: RequestBody) : Response<ResponseBody>

}