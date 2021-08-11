package pro.inmost.android.visario.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.textfield.TextInputEditText
import pro.inmost.android.visario.R

@BindingAdapter(value = ["showError"])
fun TextInputEditText.showError(textResId: Int) {
    kotlin.runCatching {
        error = context.getString(textResId)
    }
}

@BindingAdapter(value = ["loadImage"])
fun ImageView.loadImage(image: String?) {
    if (image.isNullOrBlank()) return
    Glide.with(context).load(image).into(this)
}

@BindingAdapter(value = ["chooseMenu"])
fun MaterialToolbar.chooseMenu(myContact: Boolean?) {
    myContact ?: return
    menu.clear()
    val menu = if (myContact){
        R.menu.contact_detail
    } else {
        R.menu.contact_detail_unlisted
    }
    inflateMenu(menu)
}