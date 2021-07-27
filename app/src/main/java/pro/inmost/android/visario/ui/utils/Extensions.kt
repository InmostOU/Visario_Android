package pro.inmost.android.visario.ui.utils

import android.content.Context
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

inline fun Fragment.navigate(direction: () -> NavDirections){
    kotlin.runCatching {
        findNavController().navigate(direction())
    }
}

fun Context.toast(msg: String){
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun Context.toast(textResId: Int){
    Toast.makeText(this, getString(textResId), Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(msg: String){
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.toast(textResId: Int){
    Toast.makeText(requireContext(), getString(textResId), Toast.LENGTH_SHORT).show()
}

fun Fragment.snackbar(msg: String){
    Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
}

fun Fragment.snackbar(textResId: Int){
    Snackbar.make(requireView(), requireContext().getString(textResId), Snackbar.LENGTH_SHORT).show()
}

fun View.snackbar(msg: String){
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}

fun View.snackbar(textResId: Int){
    Snackbar.make(this, context.getString(textResId), Snackbar.LENGTH_SHORT).show()
}

fun Fragment.navigateUp(){
    findNavController().navigateUp()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

val View.layoutInflater: LayoutInflater get() = LayoutInflater.from(context)

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()