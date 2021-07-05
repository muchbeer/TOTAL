package raum.muchbeer.total.fragment.hse

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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

        binding.viewModel = viewModel
        binding.lifecycleOwner= this
     //   viewModel.insertToHSE()
        viewModel.checkHseData.observe(requireActivity(), {
            if(it =="Success") {
                Toast.makeText(requireContext(), "Database inserted", Toast.LENGTH_LONG).show()
                findNavController().navigate(HseFragmentDirections.actionHseFragmentToHseListFragment())
            }
        })

        hideTextEdit()
        return  binding.root
    }


    private fun hideTextEdit() {
        viewModel.selectIncidence.observe(viewLifecycleOwner, { isIncident ->
            if (isIncident == "Yes") binding.commentForIncidence.visibility = View.VISIBLE
            else if (isIncident == "   ") binding.commentForIncidence.visibility = View.GONE
            else binding.commentForIncidence.visibility = View.GONE
        })

        viewModel.selectSecurities.observe(viewLifecycleOwner, { anySecurityIssue ->
            if (anySecurityIssue == "Yes") binding.comentForSecurityisue.visibility = View.VISIBLE
            else if (anySecurityIssue == "   ") binding.comentForSecurityisue.visibility = View.GONE
            else binding.comentForSecurityisue.visibility = View.GONE
        })

    }
}