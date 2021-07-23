package pro.inmost.android.visario.ui.utils

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController

inline fun Fragment.navigate(direction: () -> NavDirections){
    kotlin.runCatching {
        findNavController().navigate(direction())
    }
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