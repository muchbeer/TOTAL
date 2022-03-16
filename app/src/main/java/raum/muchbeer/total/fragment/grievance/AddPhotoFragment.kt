package raum.muchbeer.total.fragment.grievance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import raum.muchbeer.total.R
import raum.muchbeer.total.databinding.FragmentAddPhotoBinding



class AddPhotoFragment : DialogFragment() {

    private lateinit var binding : FragmentAddPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_photo,
            container,
            false)


        binding.apply {
            btnCancel.setOnClickListener {     dismiss()    }
        }

        return binding.root
    }

companion object {
    val TAG = AddPhotoFragment::class.simpleName
}
}