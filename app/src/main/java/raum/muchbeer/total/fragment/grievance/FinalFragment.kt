package raum.muchbeer.total.fragment.grievance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.R
import raum.muchbeer.total.databinding.FragmentFinalBinding
import raum.muchbeer.total.model.grievance.papform.PapEntryListModel
import raum.muchbeer.total.viewmodel.FinalViewModel
import raum.muchbeer.total.viewmodel.SampleVM

@AndroidEntryPoint
class FinalFragment : Fragment() {
  private val viewModel : FinalViewModel by viewModels()
 private lateinit var binding : FragmentFinalBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFinalBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.viewInformation()

        viewModel.checkOnDoneStatus.observe(viewLifecycleOwner, {
            if (it == "Success") {
                Toast.makeText(requireContext(), "Success Entered to the database", Toast.LENGTH_LONG).show()
            }
        })
return binding.root

    }


}