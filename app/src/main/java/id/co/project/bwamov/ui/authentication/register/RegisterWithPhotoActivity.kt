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
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import id.co.project.bwamov.R
import id.co.project.bwamov.databinding.ActivityRegisterWithPhotoBinding
import id.co.project.bwamov.ui.authentication.signin.User
import id.co.project.bwamov.ui.homePage.HomePageActivity
import id.co.project.bwamov.utils.Preferences
import java.util.*

class RegisterWithPhotoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterWithPhotoBinding

    // Inialisasi variable yang akan digunakan
    var REQUEST_IMAGE_CAPTURE = 1
    var statusAdd: Boolean = false
    var filePath: Uri? = null


    lateinit var storage: FirebaseStorage
    lateinit var storageReferensi: StorageReference
    lateinit var preferences: Preferences

    lateinit var user : User
    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterWithPhotoBinding.inflate(layoutInflater)

       mFirebaseInstance = FirebaseDatabase.getInstance()
       mFirebaseDatabase = mFirebaseInstance.getReference("User")
        preferences = Preferences(this)
        storage = FirebaseStorage.getInstance()
        storageReferensi = storage.getReference()

        setContentView(binding.root)
           user = intent.getParcelableExtra("data")!!
            binding.tvHello.text = getString(R.string.selamat_datang) + user.nama
            buttonBackClick()
            binding.buttonAddFoto.setOnClickListener {
                if (statusAdd) {
                    statusAdd = false
                    binding.buttonSave.visibility = View.INVISIBLE

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
                    progressDialog.show()

                    var ref = storageReferensi.child("image/" + UUID.randomUUID().toString())
                    ref.putFile(filePath!!)
                        .addOnSuccessListener {
                            progressDialog.dismiss()
                            Toast.makeText(
                                this@RegisterWithPhotoActivity,
                                "Upload Telah Selesai",
                                Toast.LENGTH_LONG
                            ).show()

                            ref.downloadUrl.addOnSuccessListener {
                                saveToFirebase(it.toString())

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
                            progressDialog.setMessage("Uploading.." +progress.toInt()+ "%")
                        }
                } else {

                }
        }

    }

    private fun saveToFirebase(url: String) {

        mFirebaseDatabase.child(user.username!!).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                user.url = url
                mFirebaseDatabase.child(user.username!!).setValue(user)

                preferences.setValue("nama", user.nama.toString())
                preferences.setValue("user", user.username.toString())
                preferences.setValue("saldo", "")
                preferences.setValue("url", "")
                preferences.setValue("email", user.email.toString())
                preferences.setValue("status", "1")
                preferences.setValue("url", url)

                finishAffinity()
                val intent = Intent(this@RegisterWithPhotoActivity,
                   HomePageActivity::class.java)
                startActivity(intent)

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RegisterWithPhotoActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })


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

    private fun buttonBackClick() {
        binding.buttonBack.setOnClickListener {
            Intent(this@RegisterWithPhotoActivity, RegisterActivity::class.java).also {
                startActivity(it)
            }
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