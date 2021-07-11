package raum.muchbeer.total

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import raum.muchbeer.total.databinding.FragmentPhotoBinding
import raum.muchbeer.total.fragment.grievance.PhotoFragment
import raum.muchbeer.total.viewmodel.SampleVM
import java.io.File

@AndroidEntryPoint
class PhotoActivity : AppCompatActivity() {

    private lateinit var binding : FragmentPhotoBinding
    private val viewModel : SampleVM by viewModels()
    private lateinit var photoFile: File


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =  DataBindingUtil.setContentView(this, R.layout.fragment_photo)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        binding.btnTakePicture.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)


            photoFile = getPhotoFile(FILE_NAME)

            // This DOESN'T work for API >= 24 (starting 2016)
            // takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFile)

            val fileProvider = FileProvider.getUriForFile(this, "edu.stanford.rkpandey.fileprovider", photoFile)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                //   startActivityForResult(takePictureIntent, REQUEST_CODE)
                resultLauncher.launch(takePictureIntent)
            } else {
                Toast.makeText(this, "Unable to open camera", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPhotoFile(fileName: String): File {
        // Use `getExternalFilesDir` on Context to access package-specific directories.

        val storageDirectory = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", storageDirectory)
    }

    var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // There are no request codes
            val data: Intent? = result.data
            //    val takenImage = data?.extras?.get("data") as Bitmap
            val takenImageInBitMap = BitmapFactory.decodeFile(photoFile.absolutePath)
            Log.d("PhotoFragment", "The value collected is: ${takenImageInBitMap}")
            Log.d("PhotoFragment", "The file value is : ${photoFile.absolutePath}")
            viewModel.convertFileImageToBase64(photoFile.absoluteFile)
            viewModel.convertFileImagefromJavaConvert(photoFile.absoluteFile)

            //    viewModel.convertToBase64(takenImageInBitMap)
            binding.imageView.setImageBitmap(takenImageInBitMap)
        }
    }


    companion object {
        private const val FILE_NAME = "giovanna"
        private const val REQUEST_CODE = 42
    }

}