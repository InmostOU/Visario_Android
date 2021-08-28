package pro.inmost.android.visario.ui.screens.meet.list

import androidx.fragment.app.FragmentManager
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.BottomSheetMeetingsMenuBinding
import pro.inmost.android.visario.ui.base.BaseBottomSheet

class MeetingsBottomSheetMenu(val callback: (MenuItem) -> Unit) : BaseBottomSheet<BottomSheetMeetingsMenuBinding>(R.layout.bottom_sheet_meetings_menu) {

    override fun onCreated() {
        binding.createNewMeeting.setOnClickListener {
            callback(MenuItem.CREATE_NEW_MEETING)
            close()
        }
        binding.joinMeeting.setOnClickListener {
            callback(MenuItem.JOIN_MEETING)
            close()
        }
    }

    enum class MenuItem{
        CREATE_NEW_MEETING,
        JOIN_MEETING
    }

    companion object{
        fun show(fm: FragmentManager, callback: (MenuItem) -> Unit){
            MeetingsBottomSheetMenu(callback).show(fm, null)
        }
    }
}