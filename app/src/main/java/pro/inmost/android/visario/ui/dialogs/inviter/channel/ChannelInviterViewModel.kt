package pro.inmost.android.visario.ui.dialogs.inviter.channel

import android.view.View
import android.widget.Button
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.channels.AddMemberToChannelUseCase
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase
import pro.inmost.android.visario.ui.dialogs.inviter.AbstractInviterViewModel
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.utils.extensions.toast

class ChannelInviterViewModel(
    fetchContactsUseCase: FetchContactsUseCase,
    private val addMemberToChannelUseCase: AddMemberToChannelUseCase
) : AbstractInviterViewModel(fetchContactsUseCase) {
    private var channelUrl: String = ""

    override fun inviteContact(view: View,  contact: ContactUI){
        view.isEnabled = false
        viewModelScope.launch {
            addMemberToChannelUseCase.invite(channelUrl, contact.url).onSuccess {
                (view as Button).text = view.context.getString(R.string.invited)
            }.onFailure {
                view.isEnabled = true
                view.toast(R.string.invitation_failed)
            }
        }
    }

    fun setChannel(url: String) {
        channelUrl = url
    }
}