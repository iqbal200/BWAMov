package id.co.project.bwamov.ui.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import id.co.project.bwamov.databinding.ActivityOnboardingOneBinding
import id.co.project.bwamov.ui.authentication.signin.LoginActivity
import id.co.project.bwamov.utils.Preferences

class OnboardingOneActivity : AppCompatActivity() {

    private lateinit var binding : ActivityOnboardingOneBinding
    lateinit var preferences : Preferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        preferences = Preferences(this)

        if (preferences.getValue("onboarding").equals("1")){
            var goSignin = Intent(this@OnboardingOneActivity, LoginActivity::class.java)
            startActivity(goSignin)
        }
        binding = ActivityOnboardingOneBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            buttonNext.setOnClickListener {
                val inten = Intent(this@OnboardingOneActivity, OnboardingTwoActivity::class.java)
                startActivity(inten)
                finishAffinity()
            }
            buttonSingin.setOnClickListener {
                preferences.setValue("onboarding","1")
                val inten = Intent(this@OnboardingOneActivity, LoginActivity::class.java)
                startActivity(inten)
                finishAffinity()
            }
        }

    }
}