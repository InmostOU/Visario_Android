package pro.inmost.android.visario.ui.screens.meet.join

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.meetings.JoinMeetingUseCase
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class JoinMeetingViewModel(
    private val joinUseCase: JoinMeetingUseCase
) : ViewModel() {
    private val _showProgressBar = MutableLiveData(false)
    private val _showToast = SingleLiveEvent<Int>()
    private val _openMeeting = SingleLiveEvent<String>()

    val showProgressBar : LiveData<Boolean> = _showProgressBar
    val showToast : LiveData<Int> = _showToast
    val openMeetingPrepare : LiveData<String> = _openMeeting

    val meetingUrl = MutableLiveData<String>()

    fun joinToMeeting(){
        _showProgressBar.value = true
        meetingUrl.value?.let { url ->
            viewModelScope.launch {
                joinUseCase.join(url).onSuccess {
                    _showProgressBar.value = false
                    _openMeeting.value = it
                }.onFailure {
                    _showProgressBar.value = false
                    _showToast.value = R.string.join_failed
                }
            }
        }
    }
}