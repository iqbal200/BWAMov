package id.co.project.bwamov.ui.homePage.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.database.*
import id.co.project.bwamov.databinding.FragmentDashboardBinding
import id.co.project.bwamov.ui.homePage.Film
import id.co.project.bwamov.ui.homePage.MovieDetailActivity
import id.co.project.bwamov.ui.homePage.adapter.ComingSoonAdapter
import id.co.project.bwamov.ui.homePage.adapter.NowPlayingAdapter
import id.co.project.bwamov.utils.Preferences
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * A simple [Fragment] subclass.
 */
class DashboardFragment : Fragment() {

    private var _binding : FragmentDashboardBinding? =null
    private val binding get() = _binding!!
    private lateinit var preferences: Preferences
    lateinit var mDatabase: DatabaseReference

    private var dataList = ArrayList<Film>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDashboardBinding.inflate(layoutInflater,container,false)
        val root : View = binding.root
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        with(binding){
            preferences = Preferences(requireActivity().applicationContext)
            mDatabase = FirebaseDatabase.getInstance().getReference("Film")

            tvName.setText(preferences.getValue("nama"))
            if (!preferences.getValue("saldo").equals("")){
                currecy(preferences.getValue("saldo")!!.toDouble(), tvSaldo)
            }

            Glide.with(this@DashboardFragment)
                .load(preferences.getValue("url"))
                .apply(RequestOptions.circleCropTransform())
                .into(ivProfle)

            Log.v("tamvan", "url "+preferences.getValue("url"))

            recycleViewNowPlaying.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            recycleViewComingsoon.layoutManager = LinearLayoutManager(requireContext().applicationContext)
            getData()
        }


    }
// add new comment
    private fun getData() {
        mDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                dataList.clear()
                for (getdataSnapshot in dataSnapshot.getChildren()) {

                    val film = getdataSnapshot.getValue(Film::class.java!!)
                    dataList.add(film!!)
                }

                if (dataList.isNotEmpty()) {
                    binding.recycleViewNowPlaying.adapter = NowPlayingAdapter(dataList) {
                        val intent = Intent(
                            context,
                            MovieDetailActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }

                    binding.recycleViewComingsoon.adapter = ComingSoonAdapter(dataList) {
                        val intent = Intent(
                            context,
                            MovieDetailActivity::class.java
                        ).putExtra("data", it)
                        startActivity(intent)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, ""+error.message, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun currecy(harga:Double, textView: TextView) {
        val localeID = Locale("in", "ID")
        val formatRupiah = NumberFormat.getCurrencyInstance(localeID)
        textView.setText(formatRupiah.format(harga as Double))

    }

}


