package pro.inmost.android.visario.ui.screens.account.security.privacy.birthdate

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
import pro.inmost.android.visario.ui.utils.log

class BirthdateSetupViewModel (
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
            R.id.radio_button_everybody -> { updateBirthdayViewers(Viewers.EVERYONE) }
            R.id.radio_button_nobody -> { updateBirthdayViewers(Viewers.NO_ONE) }
            R.id.radio_button_contacts -> {updateBirthdayViewers(Viewers.CONTACTS) }
        }
    }

    private fun updateBirthdayViewers(viewers: Viewers) {
        profile.value?.let {
            val profileForUpdate = it.copy(showBirthdateTo = viewers).toDomainProfile()
            viewModelScope.launch {
                updateProfileUseCase.update(profileForUpdate)
            }
        }
    }
}