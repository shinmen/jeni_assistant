package fr.julocorp.jenisassistant.ui.boitage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.julocorp.jenisassistant.R

class BoitageFragment : Fragment() {

    private lateinit var boitageViewModel: BoitageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        boitageViewModel =
            ViewModelProvider(this).get(BoitageViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_boitage, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        boitageViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}