package raum.muchbeer.total.fragment.hse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.R
import raum.muchbeer.total.databinding.ActivityHseBinding
import raum.muchbeer.total.databinding.FragmentHseBinding
import raum.muchbeer.total.viewmodel.HseViewModel

@AndroidEntryPoint
class HseFragment : Fragment() {

    private val viewModel : HseViewModel by viewModels()

    private lateinit var binding : FragmentHseBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHseBinding.inflate(inflater)
        viewModel.checkHseData.observe(requireActivity(), {
            if(it =="Success") {
                Toast.makeText(requireContext(), "Database inserted", Toast.LENGTH_LONG).show()
            }
        })
        return  inflater.inflate(R.layout.fragment_hse, container, false)
    }


}