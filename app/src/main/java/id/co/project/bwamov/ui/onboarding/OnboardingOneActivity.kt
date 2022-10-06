package id.co.project.bwamov.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.co.project.bwamov.databinding.ActivityLoginBinding
import id.co.project.bwamov.databinding.ActivityOnboardingOneBinding
import id.co.project.bwamov.ui.authentication.LoginActivity

class OnboardingOneActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingOneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityOnboardingOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            buttonNext.setOnClickListener {
                val inten = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
                startActivity(inten)
                finishAffinity()
            }
            buttonSingin.setOnClickListener {
                val inten = Intent(this@OnboardingOneActivity, LoginActivity::class.java)
                startActivity(inten)
                finishAffinity()
            }
        }

    }
}