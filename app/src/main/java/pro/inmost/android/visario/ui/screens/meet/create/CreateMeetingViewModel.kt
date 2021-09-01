package pro.inmost.android.visario.ui.screens.meet.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pro.inmost.android.visario.R
import pro.inmost.android.visario.domain.usecases.meetings.CreateMeetingUseCase
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

class CreateMeetingViewModel(
    private val createUseCase: CreateMeetingUseCase
) : ViewModel() {
    private val _showProgressBar = MutableLiveData(false)
    private val _showToast = SingleLiveEvent<Int>()
    private val _startMeeting = SingleLiveEvent<String>()

    val showProgressBar : LiveData<Boolean> = _showProgressBar
    val showToast : LiveData<Int> = _showToast
    val startMeeting : LiveData<String> = _startMeeting
    val meetingName = MutableLiveData<String>()

    fun createMeeting() {
        _showProgressBar.value = true
        meetingName.value?.let { name ->
            viewModelScope.launch {
                createUseCase.create(name).onSuccess {
                    _showProgressBar.value = false
                    _startMeeting.value = name
                }.onFailure {
                    _showProgressBar.value = false
                    _showToast.value = R.string.creation_fails
                }
            }
        }
    }
}