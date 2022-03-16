package raum.muchbeer.total.fragment.grievance

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import raum.muchbeer.total.HomeActivity
import raum.muchbeer.total.databinding.FragmentFinalBinding
import raum.muchbeer.total.viewmodel.FinalViewModel

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

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.checkOnDoneStatus.collect {
                    if (it == "Success") {
                        Toast.makeText(
                            requireContext(),
                            "Success Entered to the database",
                            Toast.LENGTH_LONG
                        ).show()
                        val homeIntent = Intent(requireActivity(), HomeActivity::class.java)
                        startActivity(homeIntent)
                    }
                }
            }
        }

return binding.root

    }


}