package pro.inmost.android.visario.ui.screens.channels.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelsUseCase
import pro.inmost.android.visario.ui.entities.channel.ChannelUI
import pro.inmost.android.visario.ui.entities.channel.toUIChannels
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class SearchChannelsViewModel(
    private val fetchChannelsUseCase: FetchChannelsUseCase
) : ViewModel() {
    private val _foundChannels = MutableLiveData<List<ChannelUI>>()
    val foundChannels: LiveData<List<ChannelUI>> = _foundChannels

    private val _showProgressBar = MutableLiveData(false)
    val showProgressBar : LiveData<Boolean> = _showProgressBar

    private val _openChannel = SingleLiveEvent<ChannelUI>()
    val openChannel : LiveData<ChannelUI> = _openChannel

    fun search(query: String) {
        _showProgressBar.value = true
        viewModelScope.launch {
            _foundChannels.value = fetchChannelsUseCase.search(query).toUIChannels()
            _showProgressBar.value = false
        }
    }

    fun onChannelClick(channel: ChannelUI){
        _openChannel.value = channel
    }
}