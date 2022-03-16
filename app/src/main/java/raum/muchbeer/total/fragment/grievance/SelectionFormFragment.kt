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
import raum.muchbeer.total.databinding.FragmentFormBinding
import raum.muchbeer.total.viewmodel.GrievanceViewModel

@AndroidEntryPoint
class SelectionFormFragment : Fragment() {

    private val selectionViewModel : GrievanceViewModel by viewModels()
    private lateinit var binding : FragmentFormBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFormBinding.inflate(inflater)

        binding.viewModel = selectionViewModel
        binding.lifecycleOwner = this

        binding.next.setOnClickListener {
            selectionViewModel.grievenceDB()
            findNavController().navigate(R.id.action_selectFragment_to_photoFragment)
        }
      hideTextEdit()
       return binding.root
    }

    private fun hideTextEdit() {
        selectionViewModel.inputAgreeToSign.observe(viewLifecycleOwner, {agreeToSign->
            if(agreeToSign=="No") binding.hideAgreeToSign.visibility= View.VISIBLE
            else if (agreeToSign=="   ") binding.hideAgreeToSign.visibility = View.GONE
            else binding.hideAgreeToSign.visibility = View.GONE})

        selectionViewModel.inputSatisfy.observe(viewLifecycleOwner, { satisfyContract->
            if(satisfyContract=="No") binding.hidesatisfyContract.visibility= View.VISIBLE
            else if (satisfyContract=="   ") binding.hidesatisfyContract.visibility = View.GONE
            else binding.hidesatisfyContract.visibility = View.GONE})

        selectionViewModel.inputRecommendation.observe(viewLifecycleOwner, { recommend->
            if(recommend=="No") binding.hideReconmmend.visibility= View.GONE
            else if (recommend=="   ") binding.hideReconmmend.visibility = View.GONE
            else binding.hideReconmmend.visibility = View.VISIBLE
        })

        selectionViewModel.inputGenderType.observe(viewLifecycleOwner, {  isNull->
            if (isNull.isNullOrEmpty()) binding.next.visibility = View.INVISIBLE
            else binding.next.visibility = View.VISIBLE
        })

        selectionViewModel.inputInquiryType.observe(viewLifecycleOwner, { isNull ->
            if(isNull.isNullOrEmpty()) binding.next.isEnabled = false  else
                binding.next.isEnabled = true
        })
    }

    private fun addPhoto() {
        AddPhotoFragment().show(
            childFragmentManager, AddPhotoFragment.TAG)
    }

}