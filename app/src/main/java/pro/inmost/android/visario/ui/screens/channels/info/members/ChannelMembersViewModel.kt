package pro.inmost.android.visario.ui.screens.channels.info.members

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.channels.FetchChannelMembersUseCase
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.entities.contact.toUIContacts
import pro.inmost.android.visario.utils.SingleLiveEvent
import pro.inmost.android.visario.utils.extensions.replaceAll

class ChannelMembersViewModel(
    private val fetchChannelMembersUseCase: FetchChannelMembersUseCase
) : ViewModel() {
    private val _openContactInfoEvent = SingleLiveEvent<ContactUI>()
    val openContactInfoEvent: LiveData<ContactUI> = _openContactInfoEvent
    private val members = MutableLiveData<List<ContactUI>>()
    private val allMembers = mutableListOf<ContactUI>()

    fun onMemberClick(contact: ContactUI){
        _openContactInfoEvent.value = contact
    }

    fun loadMembers(channelArn: String): LiveData<List<ContactUI>> {
        viewModelScope.launch {
            fetchChannelMembersUseCase.fetch(channelArn).map { it.toUIContacts() }.collect {
                allMembers.replaceAll(it)
                members.value = it
            }
        }
        return members
    }

    fun searchMembers(query: String){
        members.value = if (query.isNotBlank()){
            allMembers.filter { it.fullName.contains(query, true) }
        } else allMembers
    }
}