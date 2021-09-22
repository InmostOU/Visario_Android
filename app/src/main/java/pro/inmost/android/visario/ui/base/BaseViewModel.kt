package pro.inmost.android.visario.ui.base

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pro.inmost.android.visario.ui.utils.SingleLiveEvent

abstract class BaseViewModel : ViewModel(){
    private val _notificationEvent = SingleLiveEvent<Int>()
    val notificationEvent: LiveData<Int> = _notificationEvent

    fun sendNotification(@StringRes strRes: Int){
        _notificationEvent.value = strRes
    }
}