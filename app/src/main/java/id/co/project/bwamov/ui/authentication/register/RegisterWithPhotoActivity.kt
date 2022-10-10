package id.co.project.bwamov.ui.authentication.register

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import id.co.project.bwamov.R
import id.co.project.bwamov.databinding.ActivityRegisterWithPhotoBinding
import id.co.project.bwamov.ui.authentication.signin.LoginActivity
import id.co.project.bwamov.ui.homePage.HomePageActivity
import id.co.project.bwamov.utils.Preferences
import java.util.*

class RegisterWithPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterWithPhotoBinding

    // Inialisasi variable yang akan digunakan
    var REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    lateinit var filePath: Uri

    lateinit var storage: FirebaseStorage
    lateinit var storageReferensi: StorageReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterWithPhotoBinding.inflate(layoutInflater)

        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()

        setContentView(binding.root)
            binding.tvHello.text = "Selamat Datang \n" + intent.getStringExtra("nama")
            binding.buttonAddFoto.setOnClickListener {
                if (statusAdd) {
                    statusAdd = false
                    binding.buttonSave.visibility = View.VISIBLE

                    binding.buttonAddFoto.setImageResource(R.drawable.ic_add_foto)
                    binding.icProfileImage.setImageResource(R.drawable.ic_profile)

                } else {
                    ImagePicker.with(this)
                        .cameraOnly()
                        .galleryOnly().start()

                }
            }

            binding.buttonUploadNanti.setOnClickListener {
                finishAffinity()
                var goLogin = Intent(this@RegisterWithPhotoActivity,HomePageActivity::class.java)
                startActivity(goLogin)
            }
            binding.buttonSave.setOnClickListener {
                if (filePath != null) {
                    var progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Uploading...")
                    progressDialog.show()

                    var ref = storageReferensi.child("image/" + UUID.randomUUID().toString())
                    ref.putFile(filePath)
                        .addOnSuccessListener {
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@RegisterWithPhotoActivity,
                                "Upload Telah Selesai",
                                Toast.LENGTH_LONG
                            ).show()

                            ref.downloadUrl.addOnSuccessListener {
                                preferences.setValue("url", it.toString())
                            }

                            finishAffinity()
                            var goLogin =
                                Intent(this@RegisterWithPhotoActivity,HomePageActivity::class.java)
                            startActivity(goLogin)

                        }
                        .addOnFailureListener {
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@RegisterWithPhotoActivity,
                                "Failed",
                                Toast.LENGTH_LONG
                            ).show()

                        }
                        .addOnProgressListener {
                            taskSnapshot -> var progress = 100.0 * taskSnapshot.bytesTransferred/ taskSnapshot.totalByteCount
                            progressDialog.setMessage("Upload"+progress.toInt()+ "%")
                        }
                } else {

                }
        }

    }
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (resultCode == Activity.RESULT_OK) {
        //Image Uri will not be null for RESULT_OK
            statusAdd = true
            filePath = data?.data!!
            Glide.with(this)
                .load(filePath)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.icProfileImage)

        Log.v("tanvan", "file uri upload"+filePath)
        // Use Uri object instead of File to avoid storage permissions
        binding.buttonSave.visibility = View.VISIBLE
        binding.buttonAddFoto.setImageResource(R.drawable.ic_delete)
    } else if (resultCode == ImagePicker.RESULT_ERROR) {
        Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
    }
}



    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        Toast.makeText(
            this@RegisterWithPhotoActivity,
            "Buru-buru? klik tombol upload nanti aja",
            Toast.LENGTH_LONG
        ).show()
    }


}