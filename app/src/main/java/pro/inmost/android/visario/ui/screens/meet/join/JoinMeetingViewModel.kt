package pro.inmost.android.visario.ui.screens.meet.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.inmost.android.visario.R
import pro.inmost.android.visario.utils.SingleLiveEvent
import pro.inmost.android.visario.utils.extensions.isMeetingInvitation
import pro.inmost.android.visario.utils.extensions.isValidUrl
import pro.inmost.android.visario.utils.extensions.meetingId

class JoinMeetingViewModel: ViewModel() {
    private val _joinMeeting = SingleLiveEvent<String>()
    val joinMeeting : LiveData<String> = _joinMeeting

    private val _showToast = SingleLiveEvent<Int>()
    val showToast : LiveData<Int> = _showToast

    val meetingUrl = MutableLiveData<String>()

    fun joinToMeeting(){
        meetingUrl.value?.let { url ->
            if (url.isValidUrl() && url.isMeetingInvitation()){
                url.meetingId?.let { _joinMeeting.value = it }
            } else {
                _showToast.value = R.string.invalid_url
            }
        }
    }
}