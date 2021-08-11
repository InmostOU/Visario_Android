package pro.inmost.android.visario.ui.screens.account.security.privacy.password

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentPrivacyPasswordBinding
import pro.inmost.android.visario.ui.main.BaseFragment

class PasswordSetupFragment: BaseFragment<FragmentPrivacyPasswordBinding>(R.layout.fragment_privacy_password) {
    private val viewModel: PasswordSetupViewModel by viewModel()
    override fun onCreated() {

    }
}