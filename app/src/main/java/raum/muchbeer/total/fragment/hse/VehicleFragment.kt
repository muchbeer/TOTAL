package raum.muchbeer.total.fragment.hse

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.R
import raum.muchbeer.total.adapter.OnVehicleClickListener
import raum.muchbeer.total.adapter.VehicleListAdapter
import raum.muchbeer.total.databinding.FragmentVehicleBinding
import raum.muchbeer.total.model.PapState
import raum.muchbeer.total.viewmodel.VehicleViewModel

@AndroidEntryPoint
class VehicleFragment : Fragment() {

    private lateinit var binding : FragmentVehicleBinding
    private val viewModel : VehicleViewModel by viewModels()
    private lateinit var adapter : VehicleListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVehicleBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = VehicleListAdapter(OnVehicleClickListener {
            viewModel.displayFormFilling(it)
            viewModel.displayComplete()
        })

        binding.recyclerView.adapter = adapter
        callFunctionAndRetrieveData()
        return binding.root
    }

    private fun callFunctionAndRetrieveData() {


        viewModel.receiveVehicleRequested.observe(viewLifecycleOwner, {  result->

            adapter.submitList(result.data)

           // binding.vehicleProgressBar.isVisible = result is PapState.Loading && result.data.isNullOrEmpty()
            if (result.data.isNullOrEmpty()) binding.vehicleProgressBar.visibility = View.VISIBLE
              else  binding.vehicleProgressBar.visibility = View.GONE
        })

        viewModel.navigateToFormFilling.observe(viewLifecycleOwner, { vehicle ->
            if (vehicle !=null) {
               findNavController().navigate(VehicleFragmentDirections.actionVehicleFragmentToVehicleFormFragment(vehicle))
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.requestVehicles()
    }
}