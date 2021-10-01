package pro.inmost.android.visario.ui.screens.channels.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.channels.CreateChannelUseCase
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.ui.entities.channel.ChannelMode
import pro.inmost.android.visario.ui.entities.channel.ChannelPrivacy
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toDomainChannel
import pro.inmost.android.visario.utils.SingleLiveEvent
import pro.inmost.android.visario.utils.log

class CreateChannelViewModel(
    private val createChannelUseCase: CreateChannelUseCase,
    private val fetchChannelsUseCase: FetchChannelsUseCase
) : ViewModel() {
    private val _closeFragment = SingleLiveEvent<Unit>()
    val closeFragment : LiveData<Unit> = _closeFragment

    private val _showProgressBar = MutableLiveData(false)
    val showProgressBar : LiveData<Boolean> = _showProgressBar

    val name = MutableLiveData<String>()
    val description = MutableLiveData<String>()
    val public = MutableLiveData(true)
    val restricted = MutableLiveData(true)

    fun createChannel(){
        val channel = ChannelUI(
            channelArn = "",
            name = name.value ?: "",
            mode = if (public.value == true || restricted.value == true) ChannelMode.RESTRICTED else ChannelMode.UNRESTRICTED,
            privacy = if (public.value == true) ChannelPrivacy.PUBLIC else ChannelPrivacy.PRIVATE,
            description = description.value ?: "",
            isMember = true,
            isModerator = true,
            isAdmin = true,
            membersCount = 1
        )
        viewModelScope.launch {
            showProgressBar()
            createChannelUseCase.create(channel.toDomainChannel()).onSuccess {
                fetchChannelsUseCase.refresh()
                _closeFragment.call()
            }.onFailure {
                log("create channel failed: ${it.message}")
            }
        }.invokeOnCompletion { hideProgressBar()  }
    }

    private fun hideProgressBar() {
        _showProgressBar.value = false
    }

    private fun showProgressBar() {
        _showProgressBar.value = true
    }
}