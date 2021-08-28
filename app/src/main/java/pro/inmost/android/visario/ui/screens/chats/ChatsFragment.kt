package pro.inmost.android.visario.ui.screens.chats

import android.view.View
import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentChatsBinding
import pro.inmost.android.visario.ui.base.BaseFragment

class ChatsFragment : BaseFragment<FragmentChatsBinding>(R.layout.fragment_chats) {
    private val viewModel: ChatsViewModel by viewModel()

    override var bottomNavViewVisibility: Int = View.VISIBLE

    override fun onCreated() {

    }
}