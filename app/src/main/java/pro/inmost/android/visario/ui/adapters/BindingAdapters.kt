package pro.inmost.android.visario.ui.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.android.material.textfield.TextInputEditText

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