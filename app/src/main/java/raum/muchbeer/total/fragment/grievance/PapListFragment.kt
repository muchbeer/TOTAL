package raum.muchbeer.total.fragment.grievance

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import raum.muchbeer.total.adapter.OnPapClickListener
import raum.muchbeer.total.adapter.PapHomeAdapter
import raum.muchbeer.total.databinding.FragmentPapListBinding
import raum.muchbeer.total.utils.Constant.Companion.FULL_NAME
import raum.muchbeer.total.utils.Constant.Companion.PREFERENCE_NAME
import raum.muchbeer.total.utils.Constant.Companion.VALUTION_NO_API
import raum.muchbeer.total.viewmodel.LoginViewModel

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PapListFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private lateinit var adapter : PapHomeAdapter
    private lateinit var binding: FragmentPapListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPapListBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        adapter = PapHomeAdapter(OnPapClickListener {
            viewModel.displayFormFilling(it)
        })

        binding.recyclerView.adapter = adapter

     callFunctionAndRetrieveData()
        return binding.root
    }

   private fun callFunctionAndRetrieveData() {
       val sharedPreference =
           requireContext().getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
       val editor = sharedPreference.edit()


       viewLifecycleOwner.lifecycleScope.launchWhenStarted {
           viewModel.searchPapsListFlow.collect {
           }
       }

       lifecycleScope.launch {
           repeatOnLifecycle(Lifecycle.State.STARTED) {
               viewModel.receiveListOfPaps.collect { result ->
                   adapter.submitList(result.data)

                   if (result.data.isNullOrEmpty()) {
                       binding.papsProgress.visibility = View.VISIBLE
                       binding.shimmerFrameLayout.startShimmer()
                       binding.shimmerFrameLayout.visibility = View.VISIBLE
                   } else binding.papsProgress.visibility = View.GONE
                   binding.shimmerFrameLayout.stopShimmer()
                   binding.shimmerFrameLayout.visibility = View.GONE
                   binding.recyclerView.visibility = View.VISIBLE
               }
           }
       }


       lifecycleScope.launch {
           repeatOnLifecycle(Lifecycle.State.STARTED) {
               viewModel.navigateToFormFilling.collect { papList ->
                   if (papList !=null) {
                       editor.putString(VALUTION_NO_API, papList.valuation_number)
                       editor.putString(FULL_NAME, papList.full_name)
                       editor.apply()
                       //     val direction =  PapListFragmentDirections.actionPapListFragmentToSelectFragment(papList)

                       findNavController().navigate(
                           PapListFragmentDirections.actionPapListFragmentToSelectFragment(papList)
                       )
                       //  viewModel.displayComplete()
                   }
               }
           }
       }

   }

    override fun onPause() {
        super.onPause()
        binding.shimmerFrameLayout.stopShimmer()
    }

    private fun searchPaps() {
        binding.searchPaps.editText?.doOnTextChanged { searchPap, start, before, count ->
          //consider the below code when the user enter a text to clear search icon
            if(!searchPap.isNullOrBlank())
            binding.searchPaps.isStartIconVisible = false
            viewModel.setSearchQuery(search = searchPap.toString())
        }

        //manual handle on click end
        binding.searchPaps.setEndIconOnClickListener {
            //clear the text
        }
    }

    /*
  *
private fun getSearchNameFromDb(searchText: String) {
      var searchText = searchText
      searchText = "%$searchText%"

      viewModel.searchPaps(fullName = searchText).observe(requireActivity(),  { list ->
          list?.let {
              Log.e("List = ", list.toString())
          }

      })

  }*/

    /*val search = menu.findItem(R.id.searchItems)
        searchView = search.actionView as androidx.appcompat.widget.SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.
                                          SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    getSearchNameFromDb(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    getSearchNameFromDb(newText)
                }
                return true
            }

        })*/
}