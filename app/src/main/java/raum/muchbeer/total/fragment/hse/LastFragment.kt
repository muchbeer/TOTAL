package raum.muchbeer.total.fragment.hse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.adapter.VehicleFormListAdapter
import raum.muchbeer.total.databinding.FragmentVehicleFormListBinding
import raum.muchbeer.total.viewmodel.VehicleViewModel

@AndroidEntryPoint
class LastFragment : Fragment() {

    private lateinit var binding : FragmentVehicleFormListBinding
    private val viewModel : VehicleViewModel by viewModels()
    private lateinit var adapter : VehicleFormListAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentVehicleFormListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        adapter = VehicleFormListAdapter()

        binding.vehicleFormList.adapter = adapter


        displayList()
        return binding.root
    }

    private fun displayList() {
        viewModel.retrieveLiveVehicle.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.submitList(it)
            } else Log.d("FragmentDetailFragment", "Empty List of Engagefragment")
        })
    }


}