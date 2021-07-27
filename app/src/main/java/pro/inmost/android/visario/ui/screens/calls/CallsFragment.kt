package pro.inmost.android.visario.ui.screens.calls

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentCallsBinding
import pro.inmost.android.visario.ui.main.BaseFragment

class CallsFragment : BaseFragment<FragmentCallsBinding>(R.layout.fragment_calls) {
    private val viewModel: CallsViewModel by viewModel()

    override fun onCreated(binding: FragmentCallsBinding) {

    }
}