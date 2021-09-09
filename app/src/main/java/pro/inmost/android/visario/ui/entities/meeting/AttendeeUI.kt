package pro.inmost.android.visario.ui.entities.meeting

import androidx.lifecycle.MutableLiveData
import com.amazonaws.services.chime.sdk.meetings.audiovideo.AudioVideoFacade
import com.amazonaws.services.chime.sdk.meetings.audiovideo.video.VideoTileState

data class AttendeeUI(
    val tileState: VideoTileState,
    val audioVideoFacade: AudioVideoFacade,
    val userId: Long,
    val name: String,
    val image: String,
    var isMe: Boolean = false
){
    val micOn = MutableLiveData(true)
    val camOn = MutableLiveData(true)
}
