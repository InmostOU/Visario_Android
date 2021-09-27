package pro.inmost.android.visario.ui.screens.channels.list

import androidx.lifecycle.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.domain.usecases.profile.UpdateProfileUseCase
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toUIChannels
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ChannelsViewModel(
    private val fetchUseCase: FetchChannelsUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase
) : ViewModel() {
    private val _onChannelClick = SingleLiveEvent<String>()
    val onChannelClick: LiveData<String> = _onChannelClick

    private val _showProgressBar = MutableLiveData(false)
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    val channels: LiveData<List<ChannelUI>>
        get() = fetchUseCase.getChannels()
            .map { it.toUIChannels() }
            .asLiveData()

    init {
        viewModelScope.launch {
            updateProfileUseCase.refresh()
        }
    }

    fun onItemClick(item: ChannelUI) {
        _onChannelClick.value = item.channelArn
    }
}