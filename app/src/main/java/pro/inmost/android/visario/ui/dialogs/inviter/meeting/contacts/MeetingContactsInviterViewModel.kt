package pro.inmost.android.visario.ui.dialogs.inviter.meeting.contacts

import android.view.View
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.domain.usecases.contacts.FetchContactsUseCase
import pro.inmost.android.visario.ui.dialogs.inviter.AbstractInviterViewModel
import pro.inmost.android.visario.ui.entities.contact.ContactUI

class MeetingContactsInviterViewModel (
    fetchContactsUseCase: FetchContactsUseCase,
) : AbstractInviterViewModel(fetchContactsUseCase) {
    private var meetingId: String = ""

    override fun inviteContact(view: View, contact: ContactUI){
        view.isEnabled = false
        viewModelScope.launch {
         /*   inviteContactToMeetingUseCase.invite(contact.toDomainContact().id, meetingId).onSuccess {
                (view as Button).text = view.context.getString(R.string.invited)
            }.onFailure {
                view.isEnabled = true
                view.toast(R.string.invitation_failed)
            }*/
        }
    }

    fun setMeetingId(id: String) {
        meetingId = id
    }
}