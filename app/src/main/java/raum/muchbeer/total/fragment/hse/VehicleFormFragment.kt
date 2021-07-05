package raum.muchbeer.total.fragment.hse

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.R
import raum.muchbeer.total.databinding.FragmentFormBinding
import raum.muchbeer.total.databinding.FragmentVehicleBinding
import raum.muchbeer.total.databinding.FragmentVehicleFormBinding
import raum.muchbeer.total.viewmodel.VehicleViewModel

@AndroidEntryPoint
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

        /*
        *   binding = FragmentRecordedDetailBinding.inflate(inflater)
         val grievanceDetailArgs = RecordedDetailFragmentArgs.fromBundle(requireArguments()).recordedGrievance*/

        viewModel.checkDataDB.observe(viewLifecycleOwner, {
            if (it =="Success") {
                Toast.makeText(requireContext(), "Data Entered Succesfull", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_vehicleFormFragment_to_lastFragment)
            }
        })
        setCarInput()
        return binding.root
    }

    fun setCarInput()
    {
        val sharedPreference =  requireContext().getSharedPreferences("PREFERENCE_VEHICLE", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()

        val vehicle = VehicleFormFragmentArgs.fromBundle(requireArguments()).vehicleDetails
        Log.d("VehicleFormFragment", "Vehicle number is : ${vehicle.vehicle_number}")
        editor.putString("vehicle_number","${vehicle.vehicle_number}")
        editor.putString("drivers_name", "${vehicle.drivers_name}")
        editor.putString("driver_phone_number", "${vehicle.driver_phone_number}" )
        editor.apply()
        editor.commit()
    }
}