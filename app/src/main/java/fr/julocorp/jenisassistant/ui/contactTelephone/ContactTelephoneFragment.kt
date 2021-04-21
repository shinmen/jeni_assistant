package fr.julocorp.jenisassistant.ui.contactTelephone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import fr.julocorp.jenisassistant.R

class ContactTelephoneFragment : Fragment() {

    private lateinit var contactTelephoneViewModel: ContactTelephoneViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contactTelephoneViewModel =
            ViewModelProvider(this).get(ContactTelephoneViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contact_telephone, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        contactTelephoneViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}