package raum.muchbeer.total.fragment.grievance

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.adapter.OnPapClickListener
import raum.muchbeer.total.adapter.PapHomeAdapter
import raum.muchbeer.total.databinding.FragmentPapListBinding
import raum.muchbeer.total.model.PapState
import raum.muchbeer.total.viewmodel.LoginViewModel

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
       val sharedPreference =  requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)
       var editor = sharedPreference.edit()


       viewModel.receiveListOfPaps.observe(viewLifecycleOwner,  {  result ->

           adapter.submitList(result.data)

           if (result.data.isNullOrEmpty()) {
               binding.papsProgress.visibility = View.VISIBLE
               binding.shimmerFrameLayout.startShimmer()
               binding.shimmerFrameLayout.visibility = View.VISIBLE
           }
           else  binding.papsProgress.visibility = View.GONE
           binding.shimmerFrameLayout.stopShimmer()
           binding.shimmerFrameLayout.visibility = View.GONE
           binding.recyclerView.visibility = View.VISIBLE
       })

       viewModel.navigateToFormFilling.observe(viewLifecycleOwner, { papList->
           if (papList !=null) {
               editor.putString("valuation_id","${papList.valuation_number}")
               editor.putString("full_name", "${papList.full_name}")
               editor.commit()
        //     val direction =  PapListFragmentDirections.actionPapListFragmentToSelectFragment(papList)
findNavController().navigate(
    PapListFragmentDirections.actionPapListFragmentToSelectFragment(papList)
)
               viewModel.displayComplete()
           }
       })
   }

    override fun onPause() {
        super.onPause()
        binding.shimmerFrameLayout.stopShimmer()
    }

    private fun searchPaps() {
        binding.searchPaps.editText?.doOnTextChanged { searchPap, _, _, _ ->
          //consider the below code when the user enter a text to clear search icon
            if(!searchPap.isNullOrBlank())
            binding.searchPaps.isStartIconVisible = false
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