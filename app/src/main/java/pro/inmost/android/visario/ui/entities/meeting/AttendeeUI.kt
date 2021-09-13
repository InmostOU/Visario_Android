package pro.inmost.android.visario.ui.entities.meeting

import androidx.databinding.ObservableField
import com.amazonaws.services.chime.sdk.meetings.audiovideo.video.DefaultVideoRenderView

data class AttendeeUI(
    val userId: Long,
    var attendeeId: String = "",
    val name: String,
    val image: String,
    val isMe: Boolean = false,
) {
    val micOn = ObservableField(true)
    val camOn = ObservableField(true)
    var videoView: DefaultVideoRenderView? = null


    fun turnOnCam(){
        camOn.set(true)
    }

    fun turnOffCam(){
        camOn.set(false)
    }

    fun turnOnMic() {
        micOn.set(true)
    }

    fun turnOffMic() {
        micOn.set(false)
    }

    fun isCameraOn() = camOn.get() ?: false
    fun isMicOn() = micOn.get() ?: false
}
