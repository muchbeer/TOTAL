package raum.muchbeer.total

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.work.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import raum.muchbeer.total.databinding.ActivityLoginBinding
import raum.muchbeer.total.datastore.UserDataPref
import raum.muchbeer.total.model.DataState
import raum.muchbeer.total.model.users.UserModel
import raum.muchbeer.total.viewmodel.LoginViewModel
import raum.muchbeer.total.work.ServerWorker
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    lateinit var aOneTimeSync: OneTimeWorkRequest

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        checkLoginState()
        //    checkStatus()
        hideKeyboard()
        binding.forgot.setOnClickListener {
            val snackBar = Snackbar.make(
                binding.generalLayout, "Please contact admin",
                Snackbar.LENGTH_LONG
            )
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(Color.CYAN)
            val textView =
                snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.BLUE)
            snackBar.show()
        }
        sendToServer()
    }

    private fun checkStatus() {
        viewModel.read_status_Code.observe(this, {
            if (it == "200") {
                openLogin()
            } else checkLoginState()
        })
    }

    private fun checkLoginState() {
        viewModel.loginState.observe(this, {
            when (it) {
                is DataState.Success<UserModel> -> {
                    //  storeUserFieldID(it.data.field_id)
                    if (it.data.position == "Data Officer") {
                        openLogin()
                        displayProgressBar(false)

                    } else if (it.data.position == "HSE/Logistics Officer") {
                        openHse()
                        //   openDriver()
                        displayProgressBar(false)
                    } else if (it.data.position == "Engagement Officer") {
                        openEngageOffice()
                        Log.d("LoginActivity", "Entered")
                        displayProgressBar(false)
                    }

                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayErrorMsg(it.error)
                }

                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun hideKeyboard() {
        viewModel.hideKeyboardValue.observe(this, {
            if (it == "hideKeyboard")
                hideSoftKeyboard()
            binding.passwordEdt.clearFocus()
        })
        viewModel.keyboardComplete()
    }

    fun Activity.hideSoftKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).apply {
            hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    private fun displayProgressBar(isVisible: Boolean) {
        binding.progressBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    private fun displayErrorMsg(errorMsg: String?) {
        val snackBar = Snackbar.make(
            binding.generalLayout, "Invalid User. Try Again",
            Snackbar.LENGTH_LONG
        )

        if (errorMsg != null) {

            val snackBar = Snackbar.make(binding.generalLayout, errorMsg, Snackbar.LENGTH_SHORT)
            val snackBarView = snackBar.view
            snackBarView.setBackgroundColor(Color.CYAN)
            val textView =
                snackBarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
            textView.setTextColor(Color.BLUE)
            snackBar.show()

            Log.d("LoginActivity", "error is : ${errorMsg}")
        } else {
            Snackbar.make(binding.generalLayout, "Unknown Error", Snackbar.LENGTH_SHORT)
                .show()
        }
    }

    private fun openLogin() {
        val loginSelect = Intent(this, HomeActivity::class.java)
        startActivity(loginSelect)
        finish()
    }

    private fun openHse() {
        val loginSelect = Intent(this, HseActivity::class.java)
        startActivity(loginSelect)
        finish()
    }

    override fun onResume() {
        super.onResume()
        val sharedPreference = getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)

        //  checkStatus()
        val position = sharedPreference.getString("field_id", "default")
        if (position == "Data Officer") {
            openLogin()
        } else if (position == "HSE/Logistics Officer") {
            openHse()
            //   openDriver()
        } else if (position == "Engagement Officer") {
            openEngageOffice()
            Log.d("LoginActivity", "Entered")
            //  displayProgressBar(false)
        }
    }

    private fun openEngageOffice() {
        val loginSelect = Intent(this, EngageActivity::class.java)
        startActivity(loginSelect)
        finish()
    }

    private fun sendToServer() {



        val work = PeriodicWorkRequestBuilder<ServerWorker>(15, TimeUnit.MINUTES)
            .setConstraints(setConstraint())
            .build()


                WorkManager.getInstance(this).enqueue(work)

                //Get WorkManager status
                WorkManager.getInstance(this).getWorkInfoByIdLiveData(work.id).observe(this,
                    Observer {
                        if (it != null) {
                            when (it.state) {

                                WorkInfo.State.SUCCEEDED -> {
                                    val myResult = it.outputData.getString("Success")
                                    Log.e("STATUS", "SUCCEEDED")
                                }

                                WorkInfo.State.RUNNING -> {
                                    Log.e("STATUS", "RUNNING")
                                }

                                WorkInfo.State.CANCELLED -> {
                                    Log.e("STATUS", "CANCELLED")
                                }

                                WorkInfo.State.FAILED -> {
                                    Log.e("STATUS", "FAILED")
                                }
                                else -> {
                                    Log.e("STATUS", "STATUS")
                                }
                            }
                        }

                    })
            }

    private fun setConstraint(): Constraints {

        return Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED) // check internet connectivity
            //.setRequiresBatteryNotLow(true) // check battery level
            // .setRequiresCharging(true) // check charging mode
             .setRequiresStorageNotLow(true) // check storage
            // .setRequiresDeviceIdle(false) // check device idle state
            .build()

    }
}