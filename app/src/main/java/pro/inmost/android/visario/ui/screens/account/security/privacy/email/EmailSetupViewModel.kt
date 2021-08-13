package pro.inmost.android.visario.ui.screens.account.security.privacy.email

import android.widget.RadioGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.profile.FetchProfileUseCase
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCase
import pro.inmost.android.visario.ui.entities.ProfileUI
import pro.inmost.android.visario.ui.entities.Viewers
import pro.inmost.android.visario.ui.entities.toDomainProfile
import pro.inmost.android.visario.ui.entities.toUIProfile

class EmailSetupViewModel(
    private val fetchProfileUseCase: FetchProfileUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    val profile = MutableLiveData<ProfileUI>()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            fetchProfileUseCase.fetch().onSuccess {
                profile.value = it.toUIProfile()
            }
        }
    }

    fun onViewersChanged(radioGroup: RadioGroup, id: Int){
        when(id){
            R.id.radio_button_everybody -> { updateEmailViewers(Viewers.EVERYONE) }
            R.id.radio_button_nobody -> { updateEmailViewers(Viewers.NO_ONE) }
            R.id.radio_button_contacts -> {updateEmailViewers(Viewers.CONTACTS) }
        }
    }

    private fun updateEmailViewers(viewers: Viewers) {
        profile.value?.let {
            val profileForUpdate = it.copy(showEmailTo = viewers).toDomainProfile()
            viewModelScope.launch {
                updateProfileUseCase.updateInfo(profileForUpdate)
            }
        }
    }
}