package raum.muchbeer.total.fragment.grievance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.R
import raum.muchbeer.total.adapter.GrievanceAdapter
import raum.muchbeer.total.adapter.OnGrievanceClickListener
import raum.muchbeer.total.adapter.PapHomeAdapter
import raum.muchbeer.total.databinding.FragmentPapListBinding
import raum.muchbeer.total.databinding.FragmentRecordedBinding
import raum.muchbeer.total.viewmodel.FinalViewModel
import raum.muchbeer.total.viewmodel.LoginViewModel
import java.util.*

@AndroidEntryPoint
class RecordedFragment : Fragment() {

    private val viewModel: FinalViewModel by viewModels()
    private lateinit var adapter : GrievanceAdapter
    private lateinit var binding: FragmentRecordedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
binding = FragmentRecordedBinding.inflate(inflater)

        adapter = GrievanceAdapter(OnGrievanceClickListener {
            viewModel.displayFormFilling(it)
            viewModel.displayComplete()
        })

        binding.recyclerViewRecorded.adapter = adapter
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.viewdataRecorded.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.submitList(it)
            }
        })


        viewModel.navigateToRecordDetail.observe(viewLifecycleOwner, {
            if (it !=null) {
                findNavController().navigate(RecordedFragmentDirections.actionRecordedFragmentToRecordedDetailFragment(it))
            }
        })
        return binding.root
    }

}