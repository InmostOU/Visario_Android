package pro.inmost.android.visario.ui.adapters

import android.net.Uri
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

@BindingAdapter(value = ["loadImageOrPlaceholder"])
fun ImageView.loadImageOrPlaceholder(image: String?) {
    if (image.isNullOrBlank()){
        Glide.with(context).load(R.drawable.contact_placeholder).into(this)
    } else{
        kotlin.runCatching {
            Glide.with(context).load(Uri.parse(image)).into(this)
        }.onFailure {
            Glide.with(context).load(R.drawable.contact_placeholder).into(this)
        }
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