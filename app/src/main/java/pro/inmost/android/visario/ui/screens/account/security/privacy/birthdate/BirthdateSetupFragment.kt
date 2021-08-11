package pro.inmost.android.visario.ui.screens.account.security.privacy.birthdate

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentPrivacyBirthdateBinding
import pro.inmost.android.visario.ui.main.BaseFragment

class BirthdateSetupFragment : BaseFragment<FragmentPrivacyBirthdateBinding>(R.layout.fragment_privacy_birthdate) {
    private val viewModel: BirthdateSetupViewModel by viewModel()
    override fun onCreated() {

    }
}