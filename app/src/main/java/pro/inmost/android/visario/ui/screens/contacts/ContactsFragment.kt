package pro.inmost.android.visario.ui.screens.contacts

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentContactsBinding
import pro.inmost.android.visario.ui.main.BaseFragment

class ContactsFragment : BaseFragment<FragmentContactsBinding>(R.layout.fragment_contacts) {
    private val viewModel: ContactsViewModel by viewModel()

    override fun onCreated(binding: FragmentContactsBinding) {

    }
}