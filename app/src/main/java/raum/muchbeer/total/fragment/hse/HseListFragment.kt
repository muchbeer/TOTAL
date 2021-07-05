package raum.muchbeer.total.fragment.hse

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.adapter.HseAdapter
import raum.muchbeer.total.databinding.FragmentHseListBinding
import raum.muchbeer.total.viewmodel.HseViewModel

@AndroidEntryPoint
class HseListFragment : Fragment() {

    private lateinit var adapter : HseAdapter
    private lateinit var binding : FragmentHseListBinding
    private val viewModel : HseViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentHseListBinding.inflate(inflater)
        binding.lifecycleOwner = this

        adapter = HseAdapter()
        binding.hseList.adapter = adapter

        callList()
        return binding.root

    }

    private fun callList() {
        viewModel.retrieveHseLive.observe(viewLifecycleOwner,  {
            if (it != null) {
                Log.d("HseListFragment", "Retrieve data from viewModel")
                it.forEach {
                    Log.d("HseListFrag", "Hse item selected: ${it.reg_date}")
                        }
                adapter.submitList(it)
            } else Log.d("HseListFragment", "Retrieve hse failed")
        })
    }


}