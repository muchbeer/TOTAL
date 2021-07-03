package raum.muchbeer.total.fragment.hse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import raum.muchbeer.total.R
import raum.muchbeer.total.databinding.FragmentFormBinding
import raum.muchbeer.total.databinding.FragmentVehicleBinding
import raum.muchbeer.total.databinding.FragmentVehicleFormBinding
import raum.muchbeer.total.viewmodel.VehicleViewModel


class VehicleFormFragment : Fragment() {

        private lateinit var binding : FragmentVehicleFormBinding
        private val viewModel : VehicleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            binding = FragmentVehicleFormBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner= this

        viewModel.checkDataDB.observe(viewLifecycleOwner, {
            if (it =="Success") {
                Toast.makeText(requireContext(), "Data Entered Succesfull", Toast.LENGTH_LONG).show()
            }
        })
        return binding.root
    }

}