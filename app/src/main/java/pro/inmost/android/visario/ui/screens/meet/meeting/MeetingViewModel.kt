package pro.inmost.android.visario.ui.screens.meet.meeting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.inmost.android.visario.ui.entities.contact.ContactUI
import pro.inmost.android.visario.ui.utils.SingleLiveEvent
import kotlin.random.Random

class MeetingViewModel : ViewModel() {
    private val _memberJoinEvent = SingleLiveEvent<ContactUI>()
    private val _memberLeaveEvent = SingleLiveEvent<ContactUI>()
    private val _micOn = MutableLiveData(true)
    private val _camOn = MutableLiveData(true)

    val memberJoinEvent: LiveData<ContactUI> = _memberJoinEvent
    val memberLeaveEvent: LiveData<ContactUI> = _memberLeaveEvent
    val micOn: LiveData<Boolean> = _micOn
    val camOn: LiveData<Boolean> = _camOn

    init {
        joinMe()
    }

    private fun joinMe() {
        _memberJoinEvent.value = createFakeMember().apply {
            firstName = "Me"
            lastName = ""
        }
    }

    fun toggleVideo(){
        _camOn.value = !_camOn.value!!
    }

    fun toggleMic(){
        _micOn.value = !_micOn.value!!
    }

    fun removeMember(contactUI: ContactUI){
        _memberLeaveEvent.value = contactUI
    }

    fun addMember() {
        _memberJoinEvent.value = createFakeMember()
    }

    private fun createFakeMember(): ContactUI {
        val randomId = Random.nextInt(100)
        return ContactUI(
            id = randomId,
            url = "url$randomId",
            username = "username$randomId",
            firstName = "Sponge",
            lastName = "Bob-$randomId",
            email = "",
            phoneNumber = "",
            image = "",
            about = "",
            birthdate = 0L
        )
    }

    suspend fun leaveMeeting() {

    }
}