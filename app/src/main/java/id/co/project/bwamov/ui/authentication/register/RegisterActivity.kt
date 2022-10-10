package id.co.project.bwamov.ui.authentication.register

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import id.co.project.bwamov.R
import id.co.project.bwamov.databinding.ActivityRegisterBinding
import id.co.project.bwamov.ui.authentication.signin.User
import id.co.project.bwamov.utils.dismisLoading
import id.co.project.bwamov.utils.showLoading

class RegisterActivity : AppCompatActivity() {

    //Inialisai View Binding
    private lateinit var binding: ActivityRegisterBinding

    //Inialisasi Firebase
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var mFirebase: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    //Set nama variable
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var personname: String
    private lateinit var email: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Inialisai Firebase
        mFirebase = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.user))

        //Inialisai View Binding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Memanggil Fungsi Button Register
        funButtonRegister()
    }

    //Membuat Fungsi Button Register
    private fun funButtonRegister() {
        with(binding) {
            buttonRegister.setOnClickListener {
                showLoading()
                //Memanggil Fungsi Validasi Data EditText
                validateDataEditText()

            }
        }
    }

    private fun validateDataEditText() {
        with(binding) {

            //Set nama variable
            username = etUsernameRegister.text.toString()
            password = etPasswordRegister.text.toString()
            personname = etPersonNameRegister.text.toString()
            email = etEmailRegister.text.toString()
            if (username == getString(R.string.any)) {
                dismisLoading()
                etUsernameRegister.error = getString(R.string.masukkan_username_anda)
                etUsernameRegister.requestFocus()
            } else if (password == getString(R.string.any)) {
                dismisLoading()
                etPasswordRegister.error = getString(R.string.masukkan_password_anda)
                etPasswordRegister.requestFocus()
            } else if (personname == getString(R.string.any)) {
                etPersonNameRegister.error = getString(R.string.masukkan_nama_lengkap_anda)
                etPersonNameRegister.requestFocus()

            } else if (email == getString(R.string.any)) {
                etEmailRegister.error = getString(R.string.masukkan_email_anda)
            } else {
                //Memanggil Fungsi Untuk Menyimpan data user
                saveUserName(username, password, personname, email)
            }
        }
    }

    // Membuat Fungsi Menyimpan data user
    private fun saveUserName(
        iusername: String,
        ipassword: String,
        ipersonname: String,
        iemail: String
    ) {
        val user = User()
        user.username = iusername
        user.password = ipassword
        user.nama = ipersonname
        user.email = iemail

        if (iusername != null) {
            //Memanggil Fungsi Mengecek User name
            checkingUsername(iusername, user)
        }
    }

    //Membuat Fungsi Pengecekan User Name
    private fun checkingUsername(checkusername: String, checkuser: User) {

        mDatabaseReference.child(checkusername).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                val user = datasnapshot.getValue(User::class.java)

                if (user == null) {

                    mDatabaseReference.child(checkusername).setValue(checkuser)
                    val goToPhotoScreenActivity = Intent(
                        this@RegisterActivity,
                        RegisterWithPhotoActivity::class.java
                    ).putExtra("nama", checkuser.nama)
                    startActivity(goToPhotoScreenActivity)
                    finishAffinity()
                } else {
                    //Memanggil Fungsi Snackbar Ketika Sudah Digunakan
                    SnackbarUserSudahDigunakan()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@RegisterActivity, error.message, Toast.LENGTH_LONG)
                    .show()
            }

        })


    }
    // Membuat Fungsi Untuk Snackbar User sudah digunakan
    private fun SnackbarUserSudahDigunakan() {
        val snackbar =
            Snackbar.make(
                binding.layoutRegister,
                getString(R.string.user_sudah_digunakan),
                Snackbar.LENGTH_SHORT
            )
                .setAction(getString(R.string.action), null)
        snackbar.setActionTextColor(Color.BLACK)
        val snackbarview = snackbar.view
        snackbarview.setBackgroundColor(Color.RED)
        snackbar.show()
    }
}