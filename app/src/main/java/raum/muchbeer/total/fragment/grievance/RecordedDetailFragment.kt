package raum.muchbeer.total.fragment.grievance

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.R
import raum.muchbeer.total.databinding.FragmentRecordedBinding
import raum.muchbeer.total.databinding.FragmentRecordedDetailBinding

@AndroidEntryPoint
class RecordedDetailFragment : Fragment() {

    private lateinit var binding:  FragmentRecordedDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRecordedDetailBinding.inflate(inflater)
         val grievanceDetailArgs = RecordedDetailFragmentArgs.fromBundle(requireArguments()).recordedGrievance

        binding.satisfyWithContract.text = grievanceDetailArgs.satisfiedwithcontract
        binding.noSatisfyContract.text = grievanceDetailArgs.notsatisfiedwithcontract
        binding.agreeToSign.text = grievanceDetailArgs.agreetosign
        binding.noAgreeToSign.text = grievanceDetailArgs.notagreetosign
        binding.recommednation.text = grievanceDetailArgs.anyrecomendations
        binding.noRecommendation.text = grievanceDetailArgs.recomendations
        binding.status.text = grievanceDetailArgs.gstatus
        binding.grievanceTheme.text = grievanceDetailArgs.grievancetype
        binding.inquireType.text = grievanceDetailArgs.inquirytype
         return binding.root
    }

}