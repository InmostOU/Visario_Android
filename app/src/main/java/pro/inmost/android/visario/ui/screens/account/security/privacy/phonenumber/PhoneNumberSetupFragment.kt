package pro.inmost.android.visario.ui.screens.account.security.privacy.phonenumber

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentPrivacyPhoneNumberBinding
import pro.inmost.android.visario.ui.main.BaseFragment

class PhoneNumberSetupFragment: BaseFragment<FragmentPrivacyPhoneNumberBinding>(R.layout.fragment_privacy_phone_number) {
    private val viewModel: PhoneNumberSetupViewModel by viewModel()
    override fun onCreated() {

    }
}