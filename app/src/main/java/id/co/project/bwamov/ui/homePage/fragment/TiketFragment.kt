package id.co.project.bwamov.ui.homePage.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.co.project.bwamov.R
import id.co.project.bwamov.databinding.FragmentTiketBinding


class TiketFragment : Fragment() {

    private  var _binding : FragmentTiketBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTiketBinding.inflate(layoutInflater,container,false)
        val root : View = binding.root
        return root
    }

}