package id.co.project.bwamov.ui.authentication.signin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import id.co.project.bwamov.databinding.ActivityLoginBinding
import id.co.project.bwamov.ui.authentication.RegisterActivity
import id.co.project.bwamov.ui.homePage.HomePageActivity
import id.co.project.bwamov.utils.Preferences

class LoginActivity : AppCompatActivity() {

    // Inialisasi Data Binding
    lateinit var binding : ActivityLoginBinding
    //Inialisasi Database Firebase
    lateinit var mDatabase : DatabaseReference
    //Inialisai SharePreference
    lateinit var preferences : Preferences

    lateinit var iUsername : String
    lateinit var iPassword : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Inialisasi Database Firebase
        mDatabase = FirebaseDatabase.getInstance().getReference("User")
        //Inialsasi SharePreferences
        preferences = Preferences(this)

        preferences.setValue("onboarding","1")
        if (preferences.getValue("status").equals("1")){
            finishAffinity()
            var goHome = Intent(this@LoginActivity,HomePageActivity::class.java)
            startActivity(goHome)
        }
        // Inialisasi Data Binding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Memanggil Fungsi Button Login
        buttonLogin()
        // Memanggil Fungsi Button Register
        buttonRegister()
}


    //Membuat Fungsi Button Pindah Keregister Activity
    private fun buttonRegister() {
        with(binding){
            buttonRegister.setOnClickListener {
                val intent = Intent(this@LoginActivity,RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    //Membuat Fungsi Button Login
    private fun buttonLogin() {
            binding.buttonLogin.setOnClickListener{
                kondisiEditText()
        }
    }

    // Fungsi Edit Text Tidak Boleh Kosong
    private fun kondisiEditText() {
        with(binding){
            iUsername = etUsername.text.toString()
            iPassword = etPassword.text.toString()
            if (iUsername.equals("")){
                etUsername.error = "Silahkan Tulis Username Anda"
                etUsername.requestFocus()
            }else if (iPassword.equals("")){
                etPassword.error = "Silahkan Tulis Password Anda"
                etPassword.requestFocus()
            }else{
                pushLogin(iUsername,iPassword)
            }
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object: ValueEventListener{

            //Implement Member Ketika Data Ditolak
            override fun onCancelled(databaseerror: DatabaseError) {
                Toast.makeText(this@LoginActivity, databaseerror.message, Toast.LENGTH_LONG).show()
            }
            //Implement Member Ketika Ketika Data Berubah
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)

                if (user == null) {
                    Toast.makeText(this@LoginActivity, "User Tidak Ditemukan", Toast.LENGTH_LONG).show()

                }else{

                    if(user.password.equals(iPassword)){

                        preferences.setValue("nama",user.nama.toString())
                        preferences.setValue("username",user.username.toString())
                        preferences.setValue("url",user.url.toString())
                        preferences.setValue("saldo",user.saldo.toString())
                        preferences.setValue("email",user.email.toString())
                        preferences.setValue("status","1")

                        val intent = Intent(this@LoginActivity,HomePageActivity::class.java)
                        startActivity(intent)
                        finishAffinity()
                    } else{
                        Toast.makeText(this@LoginActivity, "Password Anda Salah", Toast.LENGTH_LONG).show()
                    }

                }
            }
        })
    }
}