package raum.muchbeer.total.fragment.engagement

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.R
import raum.muchbeer.total.adapter.EngagementAdapter
import raum.muchbeer.total.adapter.OnEngageClickListener
import raum.muchbeer.total.databinding.FragmentEngagementDetailBinding
import raum.muchbeer.total.viewmodel.EngageViewModel

@AndroidEntryPoint
class EngagementDetailFragment : Fragment() {

    private lateinit var binding : FragmentEngagementDetailBinding
            private lateinit var adapter: EngagementAdapter
            private val viewModel : EngageViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentEngagementDetailBinding.inflate(inflater)
        binding.lifecycleOwner =this

        adapter = EngagementAdapter(OnEngageClickListener {
            viewModel.displayFormFilling(it)
            Toast.makeText(requireContext(), "${it.user_name} has been selected", Toast.LENGTH_LONG).show()
        })

        binding.recyclerView2.adapter = adapter

        viewModel.engageLiveData.observe(viewLifecycleOwner, {
            if (it != null) {
                adapter.submitList(it)
            } else Log.d("FragmentDetailFragment", "Empty List of Engagefragment")
        })

        return binding.root
    }


}