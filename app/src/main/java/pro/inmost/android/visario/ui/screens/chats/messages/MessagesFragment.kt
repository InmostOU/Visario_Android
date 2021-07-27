package pro.inmost.android.visario.ui.screens.chats.messages

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentMessagesBinding
import pro.inmost.android.visario.ui.main.BaseFragment

class MessagesFragment : BaseFragment<FragmentMessagesBinding>(R.layout.fragment_messages) {
    private val viewModel: MessagesViewModel by viewModel()

    override fun onCreated(binding: FragmentMessagesBinding) {
        fetchData()
    }

    private fun fetchData() {

    }
}