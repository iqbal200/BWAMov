package id.co.project.bwamov.ui.authentication.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.co.project.bwamov.R
import id.co.project.bwamov.databinding.ActivityRegisterWithPhotoBinding
import id.co.project.bwamov.ui.authentication.signin.LoginActivity

class RegisterWithPhotoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterWithPhotoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterWithPhotoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding){
            buttonUploadNanti.setOnClickListener {
                val intent = Intent(this@RegisterWithPhotoActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }

    }
}