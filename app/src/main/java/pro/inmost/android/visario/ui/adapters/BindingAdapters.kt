package pro.inmost.android.visario.ui.adapters

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputEditText
import pro.inmost.android.visario.R
import pro.inmost.android.visario.ui.entities.message.AttachmentUI
import pro.inmost.android.visario.ui.entities.message.MessageUIStatus


/**
 * Show error with message
 *
 * @param textResId id of string resource
 */
@BindingAdapter(value = ["showError"])
fun TextInputEditText.showError(textResId: Int) {
    kotlin.runCatching {
        error = context.getString(textResId)
    }
}

/**
 * Load contact image or set placeholder if image is null or empty
 *
 * @param image url of image
 */
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

/**
 * Set view visible or invisible
 *
 * @param visible true if view should be visible, else false
 */
@BindingAdapter(value = ["setVisible"])
fun View.setVisible(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.INVISIBLE
}

/**
 * Show or hide current [FloatingActionButton]
 *
 * @param visible true if view should be show, else false
 */
@BindingAdapter(value = ["setVisible"])
fun FloatingActionButton.setVisible(visible: Boolean) {
    if (visible) show() else hide()
}

/**
 * Set message send status icon
 *
 * @param status [MessageUIStatus] - SEND, DELIVERED, FAIL or READ
 */
@BindingAdapter(value = ["messageStatusIcon"])
fun ImageView.messageStatusIcon(status: MessageUIStatus?) {
    val icon = when (status) {
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

/**
 * Set image into the ImageView
 *
 * @param uri [Uri] of image
 */
@BindingAdapter(value = ["loadImage"])
fun ImageView.loadImage(uri: Uri?) {
    uri ?: return
    Glide.with(context).load(uri).into(this)
}

@BindingAdapter(value = ["attach"])
fun ImageView.attach(attachmentUI: AttachmentUI?) {
    if (attachmentUI == null){
        setImageDrawable(null)
    } else {
        Glide.with(context).load(attachmentUI.uri).into(this)
    }
}

/**
 * Choose toolbar menu in contact fragment depending on whether a contact is on my list.
 *
 * @param myContact true if contact in user's personal contact list
 */
@BindingAdapter(value = ["chooseContactMenu"])
fun MaterialToolbar.chooseContactMenu(myContact: Boolean?) {
    myContact ?: return
    menu.clear()
    val menu = if (myContact) {
        R.menu.contact_detail
    } else {
        R.menu.contact_detail_unlisted
    }
    inflateMenu(menu)
}

/**
 * Visibility toolbar menu in messages fragment
 *
 * @param visible true if menu should be shown else false
 */
@BindingAdapter(value = ["visibilityMessagesMenu"])
fun MaterialToolbar.visibilityMessagesMenu(visible: Boolean?) {
    visible ?: return
    if (visible) {
        val menu = R.menu.messages_channel
        inflateMenu(menu)
    } else {
        menu.clear()
    }
}
/**
 * Select an item depending on whether the user is a moderator
 *
 * @param moderator true if user if moderator else false
 */
@BindingAdapter(value = ["selectMenuForModerator"])
fun MaterialToolbar.selectMenuForModerator(moderator: Boolean?) {
    moderator ?: return
    if (moderator) {
        menu.clear()
        val menu = R.menu.messages_channel_moderator
        inflateMenu(menu)
    } else {
        R.menu.messages_channel
    }
}
