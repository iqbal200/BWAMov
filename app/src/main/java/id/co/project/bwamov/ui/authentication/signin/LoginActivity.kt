package id.co.project.bwamov.ui.authentication.signin

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import id.co.project.bwamov.R
import id.co.project.bwamov.databinding.ActivityLoginBinding
import id.co.project.bwamov.ui.authentication.RegisterActivity
import id.co.project.bwamov.ui.homePage.HomePageActivity
import id.co.project.bwamov.utils.Preferences
import id.co.project.bwamov.utils.dismisLoading
import id.co.project.bwamov.utils.showLoading

class LoginActivity : AppCompatActivity() {

    // Inialisasi Data Binding
    private lateinit var binding : ActivityLoginBinding
    //Inialisasi Database Firebase
    private lateinit var mDatabase : DatabaseReference
    //Inialisai SharePreference
    private lateinit var preferences : Preferences

    private lateinit var iUsername : String
    private lateinit var iPassword : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inialisasi Database Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference(getString(R.string.user))
        //Inialsasi SharePreferences
        preferences = Preferences(this)


        // Inialisasi Data Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Memanggil Fungsi Sharepreferences
        funSharePreferences()
        // Memanggil Fungsi Button Login
        buttonLogin()
        // Memanggil Fungsi Button Register
        buttonRegister()
}
    // Membuat Fungsi Share Preferences untuk kondisi ketika sudah login
    private fun funSharePreferences() {
        preferences.setValue(getString(R.string.onboarding),getString(R.string.one))
        if (preferences.getValue(getString(R.string.status)).equals(getString(R.string.one))){
            finishAffinity()
            val goHome = Intent(this@LoginActivity,HomePageActivity::class.java)
            startActivity(goHome)
        }
    }

    //Membuat Fungsi Button Pindah Keregister Activity
    private fun buttonRegister() {
        with(binding){
            buttonRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }

    //Membuat Fungsi Button Login
    private fun buttonLogin() {
            binding.buttonLogin.setOnClickListener{
                showLoading()
                validateUserNameandPassword()
        }
    }

    // Fungsi Edit Text Tidak Boleh Kosong
    private fun validateUserNameandPassword(){
        with(binding){
            iUsername = etUsername.text.toString()
            iPassword = etPassword.text.toString()
            if (iUsername == getString(R.string.any)){
                dismisLoading()
                etUsername.error = getString(R.string.masukkan_username_anda)
                etUsername.requestFocus()
            }else if (iPassword == getString(R.string.any)){
                dismisLoading()
                etPassword.error = getString(R.string.masukkan_password_anda)
                etPassword.requestFocus()
            }else{
                pushLogin(iUsername,iPassword)
            }
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object: ValueEventListener{

            //Implement Member Ketika Ketika Data Berubah
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)

                if (user == null) {
                    showLoading()
                    getSnackbarUserTidakDitemukan()

                }else{

                    if(user.password.equals(iPassword)){

                        // Membuat Share Prefences
                        preferences.setValue(getString(R.string.nama),user.nama.toString())
                        preferences.setValue(getString(R.string.username),user.username.toString())
                        preferences.setValue(getString(R.string.url),user.url.toString())
                        preferences.setValue(getString(R.string.saldo),user.saldo.toString())
                        preferences.setValue(getString(R.string.email),user.email.toString())
                        preferences.setValue(getString(R.string.status),getString(R.string.one))

                        Toast.makeText(this@LoginActivity,getString(R.string.login_berhasil),
                            Toast.LENGTH_LONG).show()
                        val intent = Intent(this@LoginActivity,HomePageActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else{
                       showLoading()
                        getSnackbarPasswordSalah()
                    }

                }
            }

            //Implement Member Ketika Data Ditolak
            override fun onCancelled(databaseerror: DatabaseError) {
                Toast.makeText(this@LoginActivity, databaseerror.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun getSnackbarUserTidakDitemukan() {
        val snackbar =
            Snackbar.make(binding.layoutLogin, getString(R.string.user_not_founded), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.action), null)
        snackbar.setActionTextColor(Color.BLACK)
        val snackbarview = snackbar.view
        snackbarview.setBackgroundColor(Color.RED)
        snackbar.show()
    }

    private fun getSnackbarPasswordSalah() {
        val snackbar =
            Snackbar.make(binding.layoutLogin, getString(R.string.password_anda_salah), Snackbar.LENGTH_LONG)
                .setAction(getString(R.string.action), null)
        snackbar.setActionTextColor(Color.BLACK)
        val snackbarview = snackbar.view
        snackbarview.setBackgroundColor(Color.RED)
        snackbar.show()
    }

}