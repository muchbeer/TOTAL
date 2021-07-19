package raum.muchbeer.total.adapter

import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.EditText
import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField


@BindingAdapter("passWord")
fun listenPassWordValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("userName")
fun listenUsernameValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString())
}

@BindingAdapter("gNoAgreeToSign")
fun listenGNoAgreeToSign(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("gNoSatisfyWithContract")
fun listenGNoSatisfyWithContract(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("gNoRecommend")
fun listenGNoRecommendation(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("compesationComment")
fun listenCompesationComment(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString())
}

@BindingAdapter("photoComment")
fun listenPhotoComment(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString())
}

@BindingAdapter("agreeToSign")
fun listenAgreeToSign(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("satisfyContract")
fun listenSatisfyContract(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("inquiryType")
fun listenEnquiryType(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("genderType")
fun listenGenderType(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("recommendation")
fun listenRecommendation(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("gStatus")
fun listenGStatusEntrie(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

//hse
@BindingAdapter("toolbox")
fun listenToolboxEntrie(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("anyincidence")
fun listenToIncidenceEntrie(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("anySecurities")
fun listenToSecuritiesisueEntrie(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("areaLocation")
fun listenToAreaLocation(spinner: AppCompatSpinner, result: ObservableField<String>) {
    spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            result.set(parent?.getItemAtPosition(position) as String)
        }
    }
}

@BindingAdapter("commentForSecuirity")
fun listenToSecurityComment(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }


@BindingAdapter("commentForIncidence")
fun listenToComentIncidence(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("inspection")
fun listenToIncipaction(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("accomodation")
fun listenToAccomodation(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("observation")
fun listenToObservation(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("ward")
fun listenWarddValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("village")
fun listenVillageValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString())
}

@BindingAdapter("meetingTime")
fun listenMeetingTimeValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("noParticipant")
fun listenParticipantValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString())
}

@BindingAdapter("listOfParticipant")
fun listenListOfParticipantValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString()) }

@BindingAdapter("keypoint")
fun listenKeyPointValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString())
}

@BindingAdapter("toolboxcomment")
fun listenToolboxCommentValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString())
}

@BindingAdapter("distanceCovered")
fun listenDistanceCoveredValue(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString())
}

@BindingAdapter("hoursCovered")
fun listenToHoursCovered(editText: EditText, result: ObservableField<String>) {
    result.set(editText.text.toString())
}