package pro.inmost.android.visario.ui.screens.contacts.edit

import androidx.navigation.fragment.navArgs
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentContactEditBinding
import pro.inmost.android.visario.ui.main.BaseFragment
import pro.inmost.android.visario.ui.utils.navigateBack

class EditContactFragment : BaseFragment<FragmentContactEditBinding>(R.layout.fragment_contact_edit) {
    private val viewModel: EditContactViewModel by viewModel()
    private val args: EditContactFragmentArgs by navArgs()

    override fun onCreated() {
        binding.viewModel = viewModel
        viewModel.loadContact(args.contact)
        observeEvents()
    }

    private fun observeEvents() {
        binding.toolbar.setNavigationOnClickListener { navigateBack() }
        viewModel.navigateBack.observe(viewLifecycleOwner) { navigateBack() }
    }
}