package pro.inmost.android.visario.ui.screens.channels.messages

import androidx.fragment.app.FragmentManager
import pro.inmost.android.visario.R
import pro.inmost.android.visario.databinding.BottomSheetAttachmentMenuBinding
import pro.inmost.android.visario.ui.base.BaseBottomSheet

class BottomSheetAttachmentMenu(private val callback: (MenuItem) -> Unit) : BaseBottomSheet<BottomSheetAttachmentMenuBinding>(R.layout.bottom_sheet_attachment_menu) {

    override fun onCreated() {
        binding.attachImage.setOnClickListener {
            callback(MenuItem.IMAGE)
            close()
        }
        binding.attachFile.setOnClickListener {
            callback(MenuItem.FILE)
            close()
        }
    }

    enum class MenuItem{
        IMAGE,
        FILE
    }

    companion object {
        fun show(fm: FragmentManager, callback: (MenuItem) -> Unit){
            BottomSheetAttachmentMenu(callback).show(fm, null)
        }
    }
}