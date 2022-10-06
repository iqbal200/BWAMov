package id.co.project.bwamov.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.co.project.bwamov.databinding.ActivityOnboardingThreeBinding
import id.co.project.bwamov.ui.authentication.RegisterActivity

class OnboardingThreeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingThreeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingThreeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            buttonNext.setOnClickListener {
                val inten = Intent(this@OnboardingThreeActivity, RegisterActivity::class.java)
                startActivity(inten)
                finishAffinity()
            }
        }
    }
}