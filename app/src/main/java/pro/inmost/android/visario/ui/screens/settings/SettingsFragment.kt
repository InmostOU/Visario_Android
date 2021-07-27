package pro.inmost.android.visario.ui.screens.settings

import org.koin.androidx.viewmodel.ext.android.viewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.FragmentSettingsBinding
import pro.inmost.android.visario.ui.main.BaseFragment

class SettingsFragment : BaseFragment<FragmentSettingsBinding>(R.layout.fragment_settings) {
    private val viewModel: SettingsViewModel by viewModel()

    override fun onCreated(binding: FragmentSettingsBinding) {
        binding.viewModel = viewModel
    }
}