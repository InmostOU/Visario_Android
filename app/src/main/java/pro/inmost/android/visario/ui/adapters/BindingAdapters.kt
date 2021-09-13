package pro.inmost.android.visario.ui.adapters

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import pro.inmost.android.visario.R
import pro.inmost.android.visario.ui.entities.message.MessageUIStatus

@BindingAdapter(value = ["showError"])
fun TextInputEditText.showError(textResId: Int) {
    kotlin.runCatching {
        error = context.getString(textResId)
    }
}

@BindingAdapter(value = ["loadContactImage"])
fun ImageView.loadContactImage(image: String?) {
    kotlin.runCatching {
        Glide.with(context)
            .load(image)
            .placeholder(R.drawable.contact_placeholder)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .into(this)
    }.onFailure {
        Glide.with(context).load(R.drawable.contact_placeholder).into(this)
    }
}

@BindingAdapter(value = ["setRatio"])
fun View.setRatio(ratio: String){
    kotlin.runCatching {
        (layoutParams as ConstraintLayout.LayoutParams).dimensionRatio = ratio
    }
}

@BindingAdapter(value = ["setVisible"])
fun View.setVisible(visible: Boolean){
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter(value = ["setVisible"])
fun FloatingActionButton.setVisible(visible: Boolean){
    if (visible) show() else hide()
}

@BindingAdapter(value = ["messageStatusIcon"])
fun ImageView.messageStatusIcon(status: MessageUIStatus?) {
    val icon = when (status){
        MessageUIStatus.SENDING -> R.drawable.ic_baseline_access_time_24
        MessageUIStatus.DELIVERED -> R.drawable.ic_baseline_done_24
        MessageUIStatus.FAIL -> R.drawable.ic_baseline_error_outline_24
        MessageUIStatus.READ -> R.drawable.ic_baseline_done_all_24
        else -> null
    }
    kotlin.runCatching {
        Glide.with(context).load(icon).into(this)
    }
}

@BindingAdapter(value = ["loadImage"])
fun ImageView.loadImager(uri: Uri?) {
    uri ?: return
    Glide.with(context).load(uri).into(this)
}

@BindingAdapter(value = ["chooseContactMenu"])
fun MaterialToolbar.chooseContactMenu(myContact: Boolean?) {
    myContact ?: return
    menu.clear()
    val menu = if (myContact){
        R.menu.contact_detail
    } else {
        R.menu.contact_detail_unlisted
    }
    inflateMenu(menu)
}
/*

@BindingAdapter(value = ["bindAttendee"])
fun DefaultVideoRenderView.bindAttendee(attendee: AttendeeUI){
    attendee.audioVideoFacade.bindVideoView(this, attendee.tileState.tileId)
}*/
