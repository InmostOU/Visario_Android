package pro.inmost.android.visario.ui.screens.channels.list

import androidx.lifecycle.*
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toUIChannels
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class ChannelsViewModel(
    private val fetchUseCase: FetchChannelsUseCase
) : ViewModel() {
    private val _onChatClick = SingleLiveEvent<ChannelUI>()
    val onChatClick: LiveData<ChannelUI> = _onChatClick

    private val _showProgressBar = MutableLiveData(false)
    val showProgressBar: LiveData<Boolean> = _showProgressBar

    val channels: LiveData<List<ChannelUI>>
        get() = fetchUseCase.getChannels().asLiveData()
                .map { it.toUIChannels() }

    fun onItemClick(item: ChannelUI) {
        _onChatClick.postValue(item)
    }
}