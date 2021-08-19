package pro.inmost.android.visario.ui.screens.channels.search

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChannelsSearchBinding
import pro.inmost.android.visario.ui.base.BaseFragment

class SearchChannelsFragment : BaseFragment<FragmentChannelsSearchBinding>(R.layout.fragment_channels_search) {
    private val viewModel: SearchChannelsViewModel by viewModel()

    override fun onCreated() {

    }
}