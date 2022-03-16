package raum.muchbeer.total.fragment.grievance

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import raum.muchbeer.total.R
import raum.muchbeer.total.databinding.FragmentPhotoBinding
import raum.muchbeer.total.viewmodel.GrievanceViewModel
import java.io.File

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private lateinit var binding : FragmentPhotoBinding
    private val viewModel : GrievanceViewModel by viewModels()
    private lateinit var photoFile: File
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentPhotoBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

takePictureScope()


        binding.next.setOnClickListener {
            viewModel.attachMentDB()
            findNavController().navigate(R.id.action_photoFragment_to_finalFragment)
        }
return binding.root
    }

    private fun takePictureScope() = lifecycleScope.launch {
        binding.btnTakePicture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)


            photoFile = getPhotoFile(FILE_NAME)

            // This DOESN'T work for API >= 24 (starting 2016)
            // takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)

            val fileProvider = FileProvider.getUriForFile(requireContext(), "edu.stanford.rkpandey.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
                //   startActivityForResult(takePictureIntent, REQUEST_CODE)
                resultLauncher.launch(takePictureIntent)
            } else {
                Toast.makeText(requireContext(), "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun getPhotoFile(fileName: String): File {

        val storageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val takenImageInBitMap = BitmapFactory.decodeFile(photoFile.absolutePath)
            binding.imageView.setImageBitmap(takenImageInBitMap)
        }
    }


    companion object {
        private const val FILE_NAME = "photo.jpg"
        private const val REQUEST_CODE = 42
    }


}