package id.co.project.bwamov.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.co.project.bwamov.databinding.ActivityOnboardingTwoBinding

class OnboardingTwoActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingTwoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingTwoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        with(binding){
            buttonNext.setOnClickListener {
                val inten = Intent(this@OnboardingTwoActivity , OnboardingThreeActivity::class.java)
                startActivity(inten)
            }
        }
    }
}