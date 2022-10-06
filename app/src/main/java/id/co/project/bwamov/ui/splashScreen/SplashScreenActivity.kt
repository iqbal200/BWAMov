package id.co.project.bwamov.ui.splashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import id.co.project.bwamov.databinding.ActivitySplashScreenBinding
import id.co.project.bwamov.ui.onboarding.OnboardingOneActivity

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var handler = Handler()
        handler.postDelayed({
            val inten = Intent(this@SplashScreenActivity, OnboardingOneActivity::class.java)
            startActivity(inten)
            finish()

        },2000)
    }
}