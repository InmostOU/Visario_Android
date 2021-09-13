package pro.inmost.android.visario.ui.screens.meet.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class CreateMeetingViewModel : ViewModel() {
    private val _startMeeting = SingleLiveEvent<String>()
    val startMeeting : LiveData<String> = _startMeeting
    val meetingName = MutableLiveData<String>()

    fun createMeeting() {
        _startMeeting.value = meetingName.value
    }
}