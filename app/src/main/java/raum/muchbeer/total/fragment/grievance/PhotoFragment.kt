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
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.R
import raum.muchbeer.total.databinding.FragmentPhotoBinding
import raum.muchbeer.total.viewmodel.SampleVM
import java.io.File

@AndroidEntryPoint
class PhotoFragment : Fragment() {

    private lateinit var binding : FragmentPhotoBinding

    private val viewModel : SampleVM by viewModels()
    private lateinit var photoFile: File
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentPhotoBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


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

        binding.next.setOnClickListener {
            viewModel.attachMentDB()
            findNavController().navigate(R.id.action_photoFragment_to_finalFragment)
        }
return binding.root
    }

    private fun getPhotoFile(fileName: String): File {
        // Use `getExternalFilesDir` on Context to access package-specific directories.

        val storageDirectory = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
        //    val takenImage = data?.extras?.get("data") as Bitmap
            val takenImage = BitmapFactory.decodeFile(photoFile.absolutePath)
            Log.d("PhotoFragment", "The value collected is: ${takenImage}")
            viewModel.convertToBase64(takenImage)
            binding.imageView.setImageBitmap(takenImage)
        }
    }


    companion object {
        private const val FILE_NAME = "photo.jpg"
        private const val REQUEST_CODE = 42
    }


}