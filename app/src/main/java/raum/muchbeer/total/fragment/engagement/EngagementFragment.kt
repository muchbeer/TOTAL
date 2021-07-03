package raum.muchbeer.total.fragment.engagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.R
import raum.muchbeer.total.databinding.FragmentEngagementBinding
import raum.muchbeer.total.viewmodel.EngageViewModel

@AndroidEntryPoint
class EngagementFragment : Fragment() {

    private val viewModel : EngageViewModel by viewModels()
    private lateinit var binding : FragmentEngagementBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentEngagementBinding.inflate(inflater)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        return binding.root
    }

}