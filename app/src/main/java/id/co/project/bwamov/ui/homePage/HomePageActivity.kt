package id.co.project.bwamov.ui.homePage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.transaction
import id.co.project.bwamov.R
import id.co.project.bwamov.databinding.ActivityHomePageBinding
import id.co.project.bwamov.ui.homePage.fragment.DashboardFragment
import id.co.project.bwamov.ui.homePage.fragment.ProfileFragment
import id.co.project.bwamov.ui.homePage.fragment.TiketFragment

class HomePageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentHome = DashboardFragment()
        val fragmentTiket = TiketFragment()
        val fragmentProfile = ProfileFragment()

        setFragment(fragmentHome)
        with(binding){
            menu1.setOnClickListener{
                    setFragment(fragmentHome)
                changeIcon(menu1, R.drawable.ic_home_active)
                changeIcon(menu2, R.drawable.ic_tiket)
                changeIcon(menu3, R.drawable.ic_profile)
            }
            menu2.setOnClickListener{
                    setFragment(fragmentTiket)
                changeIcon(menu1, R.drawable.ic_home)
                changeIcon(menu2, R.drawable.ic_tiket_active)
                changeIcon(menu3, R.drawable.ic_profile)
            }
            menu3.setOnClickListener{
                    setFragment(fragmentProfile)
                changeIcon(menu1, R.drawable.ic_home)
                changeIcon(menu2, R.drawable.ic_tiket)
                changeIcon(menu3, R.drawable.ic_profile_active)
            }
        }
    }
    private fun setFragment(fragment: Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.layout_frame,fragment)
            fragmentTransaction.commit()
    }
    private fun changeIcon(imageView: ImageView,int:Int){
        imageView.setImageResource(int)
    }
}