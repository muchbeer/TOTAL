package raum.muchbeer.total.datastore

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "UserDataPref"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data")

@Singleton
class UserDataPref @Inject constructor(@ApplicationContext val context: Context)  {


    companion object {

        //**********GRIEVANCE******************
        val PREF_KEY_FULL_NAME = stringPreferencesKey("g_full_name")
        val PREF_KEY_STATUS = stringPreferencesKey("g_status")
        val PREF_KEY_STATUS_DESC = stringPreferencesKey("g_status_desc")
        val PREF_KEY_POSITION = stringPreferencesKey("g_position")
        val PREF_KEY_PHONENUMBER_G = stringPreferencesKey("g_phone_name")
        val PREF_KEY_USER_STATUS = stringPreferencesKey("g_user_status")
        val PREF_KEY_USER_ID = stringPreferencesKey("g_user_id")
        val PREF_KEY_FIELD_ID_2 = stringPreferencesKey("g_field_id_2")
        val PREF_KEY_VALUATION_ID = stringPreferencesKey("g_valuation_id")

        val PREF_KEY_G_STATUS_ID = stringPreferencesKey("g_entries_field_id")
        val PREF_KEY_G_NO_CONTRACT = stringPreferencesKey("g_entries_no_contract")
        val PREF_KEY_G_NO_AGREE_SIGN = stringPreferencesKey("g_entries_no_sign_agreement")
        val PREF_KEY_G_NO_RECOMENDATION = stringPreferencesKey("g_entries_no_recommendation")

        val PREF_KEY_AGREETOSIGN = stringPreferencesKey("agreetosign")
        val PREF_KEY_satisfiedwithcontract = stringPreferencesKey("satisfiedwithcontract")
        val PREF_KEY_anyrecomendations = stringPreferencesKey("anyrecomendation")

        val PREF_KEY_PHOTOLINK = stringPreferencesKey("photolink")
        val PREF_KEY_COMPASATION = stringPreferencesKey("compasation")
        val PREF_KEY_COMPASATION_COMMENT = stringPreferencesKey("compesationComment")
    }

    suspend fun storeFullName_G(fullName :String) {
        context.dataStore.edit {
            it[UserDataPref.PREF_KEY_FULL_NAME] = fullName
        }
    }


    suspend fun storeValuationId(valuationID : String) {
        context.dataStore.edit {
            it[UserDataPref.PREF_KEY_VALUATION_ID] = valuationID
        }
    }

    suspend fun storeStatus(status :String) {
        context.dataStore.edit {
            it[UserDataPref.PREF_KEY_STATUS] = status
        }
    }
   suspend fun storeCompesationComment(compesationComment :String) {
       context.dataStore.edit {
           it[PREF_KEY_COMPASATION_COMMENT] = compesationComment
       }
   }

    suspend fun storeCompesation(compesation:String) {
        context.dataStore.edit {
            it[PREF_KEY_COMPASATION] = compesation
        }
    }

    val read_user_compesation: Flow<String> = context.dataStore.data
        .map {

            it[PREF_KEY_COMPASATION] ?: "NONE"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }
    suspend fun storeUserFieldId2(userfieldID :String) {
        context.dataStore.edit {
            it[PREF_KEY_COMPASATION] = userfieldID
        }
    }


    val read_user_field_id: Flow<String> = context.dataStore.data
        .map {

            it[PREF_KEY_FIELD_ID_2] ?: "NONE"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    val read_valuation_id : Flow<String> = context.dataStore.data
        .map {
            it[UserDataPref.PREF_KEY_VALUATION_ID] ?: "NOTHING"
        }.catch {  exception ->
            if(exception is IOException) {
                Log.e(TAG, "Error reading valuation is: ${exception}", exception) } else
            {
                throw exception
            }
        }

    val read_status: Flow<String> = context.dataStore.data
        .map {

            it[UserDataPref.PREF_KEY_STATUS] ?: "2000"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    suspend fun storeStatusDesc(statusDesc :String) {
        context.dataStore.edit {
            it[UserDataPref.PREF_KEY_STATUS_DESC] = statusDesc
        }
    }

    val read_status_desc: Flow<String> = context.dataStore.data
        .map {

            it[UserDataPref.PREF_KEY_STATUS_DESC] ?: "Invalid User"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    suspend fun storePosition(position :String) {
        context.dataStore.edit {
            it[UserDataPref.PREF_KEY_POSITION] = position
        }
    }

    val read_position: Flow<String> = context.dataStore.data
        .map {

            it[UserDataPref.PREF_KEY_POSITION] ?: "admin"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    suspend fun storePhoneNumber_G(phoneNumber :String) {
        context.dataStore.edit {
            it[UserDataPref.PREF_KEY_PHONENUMBER_G] = phoneNumber
        }
    }

    val read_phone_number: Flow<String> = context.dataStore.data
        .map {

            it[UserDataPref.PREF_KEY_PHONENUMBER_G] ?: "255"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    suspend fun storeUserStatus(userStatus :String) {
        context.dataStore.edit {
            it[UserDataPref.PREF_KEY_USER_STATUS] = userStatus
        }
    }

    val read_user_status: Flow<String> = context.dataStore.data
        .map {

            it[UserDataPref.PREF_KEY_USER_STATUS] ?: "204"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    suspend fun storeUserId(userID :String) {
        context.dataStore.edit {
            it[UserDataPref.PREF_KEY_USER_ID] = userID
        }
    }

    val read_user_photo: Flow<String> = context.dataStore.data
        .map {

            it[UserDataPref.PREF_KEY_PHOTOLINK] ?: "https://"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    suspend fun storeGstatus(gEntriesStatus :String) {
        context.dataStore.edit {
            it[PREF_KEY_G_STATUS_ID] = gEntriesStatus
        }
    }

    suspend fun storeGNoAgreeToSign(gEntries :String) {
        context.dataStore.edit {
            it[PREF_KEY_G_NO_AGREE_SIGN] = gEntries
        }
    }

    suspend fun storeGNoRecommendation(gEntries :String) {
        context.dataStore.edit {
            it[PREF_KEY_G_NO_RECOMENDATION] = gEntries
        }
    }
    suspend fun storeGNoContractSatisfy(gEntries :String) {
        context.dataStore.edit {
            it[PREF_KEY_G_NO_CONTRACT] = gEntries
        }
    }

    suspend fun storePhoto(userPhoto: String) {
        context.dataStore.edit {
            it[PREF_KEY_PHOTOLINK] = userPhoto
        }
    }

    suspend fun storeCompasation(compasation: String) {
        context.dataStore.edit {
            it[PREF_KEY_COMPASATION] = compasation
        }
    }

    val read_compasation_pap: Flow<String> = context.dataStore.data
        .map {

            it[UserDataPref.PREF_KEY_COMPASATION] ?: "Default"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    val read_compasation_comment: Flow<String> = context.dataStore.data
        .map {

            it[PREF_KEY_COMPASATION_COMMENT] ?: "Default"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    suspend fun storeAgrreToSign(userAgree: String) {
        context.dataStore.edit {
            it[PREF_KEY_AGREETOSIGN] = userAgree
        }
    }

    suspend fun storeSatisfySign(userSatisfy: String) {
        context.dataStore.edit {
            it[PREF_KEY_satisfiedwithcontract] = userSatisfy
        }
    }

    suspend fun storeRecommendation(userRecommenadtion: String) {
        context.dataStore.edit {
            it[PREF_KEY_anyrecomendations] = userRecommenadtion
        }
    }

    val read_photo : Flow<String> = context.dataStore.data
        .map {
            it[PREF_KEY_PHOTOLINK] ?: "NONE"
        }

    val read_agreeToSign : Flow<String> = context.dataStore.data
        .map {
            it[PREF_KEY_AGREETOSIGN] ?: "No"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    val read_satisfyContract : Flow<String> = context.dataStore.data
        .map {
            it[PREF_KEY_satisfiedwithcontract] ?: "yes"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    val read_recommendation : Flow<String> = context.dataStore.data
        .map {
            it[PREF_KEY_anyrecomendations] ?: "beers"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }



    val read_g_entries_status: Flow<String> = context.dataStore.data
        .map {

            it[PREF_KEY_G_STATUS_ID] ?: "default"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    val read_g_no_agree_to_sign: Flow<String> = context.dataStore.data
        .map {

            it[PREF_KEY_G_NO_AGREE_SIGN] ?: "default"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    val read_g_no_satisfy_with_contract: Flow<String> = context.dataStore.data
        .map {

            it[PREF_KEY_G_NO_CONTRACT] ?: "default"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    val read_g_no_recommendation: Flow<String> = context.dataStore.data
        .map {

            it[PREF_KEY_G_NO_RECOMENDATION] ?: "default"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }

    val read_fullname: Flow<String> = context.dataStore.data
        .map {

            it[UserDataPref.PREF_KEY_FULL_NAME] ?: "Gadiel"
        }.catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences is ${exception}", exception)
            } else {
                throw exception
            }
        }



    suspend fun removeValuationNo() {
        context.dataStore.edit {  it.remove(UserDataPref.PREF_KEY_VALUATION_ID)     }
    }

    suspend fun removeAgreeToSign() {
        context.dataStore.edit {  it.remove(PREF_KEY_AGREETOSIGN)     }
    }

    suspend fun removeNoAgreeToSign() {
        context.dataStore.edit {  it.remove(PREF_KEY_G_NO_AGREE_SIGN)     }
    }

    suspend fun removeSatisfyToSign() {
        context.dataStore.edit {  it.remove(PREF_KEY_satisfiedwithcontract)     }
    }

    suspend fun removeNoSatisfyToSign() {
        context.dataStore.edit {  it.remove(PREF_KEY_G_NO_CONTRACT)     }
    }

    suspend fun removeRecommendation() {
        context.dataStore.edit {  it.remove(PREF_KEY_anyrecomendations)     }
    }

    suspend fun removeNoRecommendation() {
        context.dataStore.edit {  it.remove(PREF_KEY_G_NO_RECOMENDATION)     }
    }

    suspend fun removeEntryStatus() {
        context.dataStore.edit {  it.remove(PREF_KEY_G_STATUS_ID)     }
    }

    suspend fun removePhotoUrl() {
        context.dataStore.edit {  it.remove(PREF_KEY_PHOTOLINK)     }
    }

}
